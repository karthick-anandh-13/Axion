import { useQuery } from "@tanstack/react-query";
import api from "../api/axios";
import type { Transaction } from "../types/transaction";

export function useTransactions() {
  return useQuery({
    queryKey: ["transactions"],
    queryFn: async (): Promise<Transaction[]> => {
      const response = await api.get<Transaction[]>("/v1/ledger/transactions");
      return response.data;
    },
  });
}
