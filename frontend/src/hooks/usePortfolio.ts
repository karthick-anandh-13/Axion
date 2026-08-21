import { useQuery } from "@tanstack/react-query";
import { getPortfolioSummary } from "../api/portfolio";

export function usePortfolio() {
  return useQuery({
    queryKey: ["portfolio-summary"],
    queryFn: getPortfolioSummary,
  });
}