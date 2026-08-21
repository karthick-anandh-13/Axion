import api from "./axios";
import type {
  CreateLoanRequest,
  LoanResponse,
} from "../types/loan";

export async function createLoan(
  data: CreateLoanRequest
): Promise<LoanResponse> {
  const response = await api.post<LoanResponse>(
    "/v1/loans",
    data
  );

  return response.data;
}