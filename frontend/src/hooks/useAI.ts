import { useQuery } from "@tanstack/react-query";
import { getCreditInsight } from "../api/ai";

export function useAI() {
  return useQuery({
    queryKey: ["ai-credit"],
    queryFn: getCreditInsight,
  });
}