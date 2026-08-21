import { useQuery } from "@tanstack/react-query";
import { getRepaymentSummary } from "../api/repayment";

export function useRepayment() {
  return useQuery({
    queryKey: ["repayment"],
    queryFn: getRepaymentSummary,
  });
}