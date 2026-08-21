import api from "./axios";
import type { Transaction } from "../types/transaction";

export async function getTransactions(): Promise<Transaction[]> {
  const response = await api.get("/v1/transactions");
  return response.data;
}