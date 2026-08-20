package com.axion.kyc.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.axion.kyc.storage.DocumentStorageService;
import com.axion.kyc.storage.KycFileValidationService;
import com.axion.customer.entity.Customer;
import com.axion.customer.exception.CustomerNotFoundException;
import com.axion.customer.repository.CustomerRepository;
import com.axion.kyc.dto.CreateKycDocumentRequest;
import com.axion.kyc.dto.KycDocumentResponse;
import com.axion.kyc.entity.KycDocument;
import com.axion.kyc.entity.KycDocumentStatus;
import com.axion.kyc.entity.KycStatus;
import com.axion.kyc.entity.KycVerification;
import com.axion.kyc.exception.KycDocumentNotFoundException;
import com.axion.kyc.exception.KycNotFoundException;
import com.axion.kyc.mapper.KycDocumentMapper;
import com.axion.kyc.repository.KycDocumentRepository;
import com.axion.kyc.repository.KycRepository;
import com.axion.kyc.service.DocumentAnalysisService;
import com.axion.kyc.service.KycDocumentService;

@Service
@Transactional
public class KycDocumentServiceImpl
        implements KycDocumentService {

    private final CustomerRepository customerRepository;
    private final KycRepository kycRepository;
    private final KycDocumentRepository documentRepository;
    private final DocumentStorageService documentStorageService;
    private final KycFileValidationService fileValidationService;
    private final DocumentAnalysisService documentAnalysisService;
    
    public KycDocumentServiceImpl(
            CustomerRepository customerRepository,
            KycRepository kycRepository,
            KycDocumentRepository documentRepository,
            DocumentStorageService documentStorageService,
            KycFileValidationService fileValidationService,
            DocumentAnalysisService documentAnalysisService) {

        this.customerRepository = customerRepository;
        this.kycRepository = kycRepository;
        this.documentRepository = documentRepository;
        this.documentStorageService = documentStorageService;
        this.fileValidationService = fileValidationService;
        this.documentAnalysisService = documentAnalysisService;
    }

    @Override
public KycDocumentResponse createDocument(
        UUID userId,
        CreateKycDocumentRequest request,
        MultipartFile file) {

    Customer customer = findCustomerByUserId(userId);

    KycVerification kyc =
            kycRepository.findByCustomerId(
                    customer.getId()
            ).orElseThrow(() ->
                    new KycNotFoundException(
                            "KYC verification not found. "
                            + "Create a KYC verification first."
                    ));

    if (kyc.getStatus() == KycStatus.VERIFIED) {
        throw new IllegalStateException(
                "Documents cannot be added after KYC verification."
        );
    }

    fileValidationService.validate(file);

    if (documentRepository
            .existsByKycVerificationIdAndDocumentType(
                    kyc.getId(),
                    request.getDocumentType())) {

        throw new IllegalStateException(
                "A document of this type already exists."
        );
    }

    KycDocument document = KycDocument.builder()
            .kycVerification(kyc)
            .documentType(request.getDocumentType())
            .status(KycDocumentStatus.UPLOADED)
            .originalFileName(file.getOriginalFilename())
            .contentType(file.getContentType())
            .fileSize(file.getSize())
            .issuedDate(request.getIssuedDate())
            .expiryDate(request.getExpiryDate())
            .build();

    KycDocument savedDocument =
            documentRepository.save(document);

    try {

        String storageReference =
                documentStorageService.store(
                        file,
                        customer.getId(),
                        savedDocument.getId()
                );

        savedDocument.setStorageReference(
                storageReference
        );

        KycDocument finalDocument =
                documentRepository.save(savedDocument);

        return KycDocumentMapper.toResponse(
                finalDocument
        );

    } catch (RuntimeException exception) {

        documentRepository.delete(savedDocument);

        throw exception;
    }
}

    @Override
    @Transactional(readOnly = true)
    public List<KycDocumentResponse> getMyDocuments(
            UUID userId) {

        Customer customer = findCustomerByUserId(userId);

        KycVerification kyc =
                kycRepository.findByCustomerId(
                        customer.getId()
                ).orElseThrow(() ->
                        new KycNotFoundException(
                                "KYC verification not found."
                        ));

        return documentRepository
                .findByKycVerificationId(kyc.getId())
                .stream()
                .map(KycDocumentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public KycDocumentResponse getMyDocument(
            UUID userId,
            UUID documentId) {

        Customer customer = findCustomerByUserId(userId);

        KycVerification kyc =
                kycRepository.findByCustomerId(
                        customer.getId()
                ).orElseThrow(() ->
                        new KycNotFoundException(
                                "KYC verification not found."
                        ));

        KycDocument document =
                documentRepository
                        .findByIdAndKycVerificationId(
                                documentId,
                                kyc.getId()
                        )
                        .orElseThrow(() ->
                                new KycDocumentNotFoundException(
                                        "KYC document not found."
                                ));

        return KycDocumentMapper.toResponse(document);
    }

    @Override
    public void deleteMyDocument(
            UUID userId,
            UUID documentId) {

        Customer customer = findCustomerByUserId(userId);

        KycVerification kyc =
                kycRepository.findByCustomerId(
                        customer.getId()
                ).orElseThrow(() ->
                        new KycNotFoundException(
                                "KYC verification not found."
                        ));

        KycDocument document =
                documentRepository
                        .findByIdAndKycVerificationId(
                                documentId,
                                kyc.getId()
                        )
                        .orElseThrow(() ->
                                new KycDocumentNotFoundException(
                                        "KYC document not found."
                                ));

        if (document.getStatus() == KycDocumentStatus.VERIFIED) {
            throw new IllegalStateException(
                    "Verified documents cannot be deleted."
            );
        }

        String storageReference = document.getStorageReference();
        documentRepository.delete(document);
        documentStorageService.delete(storageReference);
    }

    private Customer findCustomerByUserId(
            UUID userId) {

        return customerRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer profile not found."
                        ));
    }
}