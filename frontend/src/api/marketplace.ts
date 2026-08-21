import api from "./axios";
import type { BorrowerOpportunity } from "../types/marketplace";

export async function getMarketplace() {
  const response =
    await api.get<BorrowerOpportunity[]>(
      "/v1/marketplace"
    );

  return response.data;
}