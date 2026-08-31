import { useQuery } from "@tanstack/react-query";
import { getMarketplace } from "../api/marketplace";

export default function useMarketplace() {
  return useQuery({
    queryKey: ["marketplace"],
    queryFn: getMarketplace,
  });
}
