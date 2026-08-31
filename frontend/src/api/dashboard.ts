import api from "./axios";
import type { DashboardSummary } from "../types/dashboard";

export async function getDashboardSummary(): Promise<DashboardSummary> {
  const response = await api.get<DashboardSummary>("/v1/dashboard");
  return response.data;
}
