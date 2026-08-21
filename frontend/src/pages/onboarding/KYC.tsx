import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Upload, FileCheck, Camera } from "lucide-react";

import StepperLayout from "../../layouts/StepperLayout";
import GlassCard from "../../components/ui/GlassCard";
import PrimaryButton from "../../components/ui/PrimaryButton";

export default function KYC() {
  const navigate = useNavigate();

  const [panUploaded, setPanUploaded] = useState(false);
  const [aadhaarUploaded, setAadhaarUploaded] = useState(false);
  const [selfieUploaded, setSelfieUploaded] = useState(false);

  const completed =
    panUploaded && aadhaarUploaded && selfieUploaded;

  const UploadBox = ({
    title,
    subtitle,
    uploaded,
    onUpload,
  }: {
    title: string;
    subtitle: string;
    uploaded: boolean;
    onUpload: () => void;
  }) => (
    <button onClick={onUpload} className="w-full text-left">
      <GlassCard
        hover
        className={`p-5 transition-all duration-300 ${
          uploaded
            ? "border-[#C7F5D9]/50 bg-[#C7F5D9]/10"
            : ""
        }`}
      >
        <div className="flex items-center gap-4">
          <div
            className={`flex h-14 w-14 items-center justify-center rounded-2xl ${
              uploaded ? "bg-[#C7F5D9]/15" : "bg-white/5"
            }`}
          >
            {uploaded ? (
              <FileCheck className="text-[#C7F5D9]" size={24} />
            ) : (
              <Upload className="text-[#F6E7C8]" size={22} />
            )}
          </div>

          <div className="flex-1">
            <h3 className="text-lg text-white">{title}</h3>
            <p className="mt-1 text-sm text-white/50">
              {subtitle}
            </p>
          </div>

          {uploaded && (
            <div className="rounded-full bg-[#C7F5D9] px-3 py-1 text-xs font-semibold text-black">
              Verified
            </div>
          )}
        </div>
      </GlassCard>
    </button>
  );

  return (
    <StepperLayout
      step={6}
      total={7}
      title="Verify your identity"
      subtitle="Secure verification takes less than a minute."
    >
      <div className="space-y-5">
        <UploadBox
          title="PAN Card"
          subtitle="Upload the front side"
          uploaded={panUploaded}
          onUpload={() => setPanUploaded(true)}
        />

        <UploadBox
          title="Aadhaar Card"
          subtitle="Front or digital copy"
          uploaded={aadhaarUploaded}
          onUpload={() => setAadhaarUploaded(true)}
        />

        <button
          onClick={() => setSelfieUploaded(true)}
          className="w-full text-left"
        >
          <GlassCard
            hover
            className={`p-5 transition-all duration-300 ${
              selfieUploaded
                ? "border-[#C7F5D9]/50 bg-[#C7F5D9]/10"
                : ""
            }`}
          >
            <div className="flex items-center gap-4">
              <div
                className={`flex h-14 w-14 items-center justify-center rounded-2xl ${
                  selfieUploaded
                    ? "bg-[#C7F5D9]/15"
                    : "bg-white/5"
                }`}
              >
                <Camera
                  className={
                    selfieUploaded
                      ? "text-[#C7F5D9]"
                      : "text-[#F6E7C8]"
                  }
                  size={22}
                />
              </div>

              <div>
                <h3 className="text-lg text-white">
                  Selfie Verification
                </h3>
                <p className="mt-1 text-sm text-white/50">
                  AI facial verification
                </p>
              </div>
            </div>
          </GlassCard>
        </button>

        <div className="rounded-2xl bg-white/5 p-4">
          <div className="mb-3 flex justify-between text-sm">
            <span className="text-white/50">
              Verification Progress
            </span>
            <span className="text-[#C7F5D9]">
              {[panUploaded, aadhaarUploaded, selfieUploaded].filter(Boolean).length}/3
            </span>
          </div>

          <div className="h-2 overflow-hidden rounded-full bg-white/10">
            <div
              style={{
                width: `${
                  ([panUploaded, aadhaarUploaded, selfieUploaded].filter(Boolean).length / 3) *
                  100
                }%`,
              }}
              className="h-full rounded-full bg-gradient-to-r from-[#C7F5D9] to-[#F6E7C8] transition-all duration-500"
            />
          </div>
        </div>

        <PrimaryButton
          disabled={!completed}
          onClick={() => navigate("/onboarding/ai")}
        >
          Complete Verification
        </PrimaryButton>
      </div>
    </StepperLayout>
  );
}