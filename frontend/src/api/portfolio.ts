import api from "./axios";
import type { PortfolioSummary } from "../types/portfolio";

export async function getPortfolioSummary() {
  const response = await api.get<PortfolioSummary>(
    "/v1/portfolio/summary"
  );

  return response.data;
}