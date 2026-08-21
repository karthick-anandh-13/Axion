import api from "./axios";
import type { RepaymentSummary } from "../types/repayment";

export async function getRepaymentSummary() {
  const response = await api.get<RepaymentSummary>(
    "/v1/repayment/summary"
  );

  return response.data;
}