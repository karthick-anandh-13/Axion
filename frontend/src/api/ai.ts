import api from "./axios";
import type { CreditInsight } from "../types/ai";

export async function getCreditInsight() {
  const response =
    await api.get<CreditInsight>(
      "/v1/ai/credit-insight"
    );

  return response.data;
}