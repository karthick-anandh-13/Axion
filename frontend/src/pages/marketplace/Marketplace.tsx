import { useMemo, useState } from "react";
import { motion } from "framer-motion";

import DashboardLayout from "../../layouts/DashboardLayout";
import SearchBar from "../../components/marketplace/SearchBar";
import FilterChips from "../../components/marketplace/FilterChips";
import BorrowerCard from "../../components/marketplace/BorrowerCard";
import Skeleton from "../../components/ui/Skeleton";
import useMarketplace from "../../hooks/useMarketplace";

import type { BorrowerOpportunity } from "../../types/marketplace";

export default function Marketplace() {
  const [search, setSearch] = useState("");
  const [filter, setFilter] = useState("All");

  const marketplaceParams = {
    search,
    filter,
  };

  const marketplace = useMarketplace(marketplaceParams as any) as {
    borrowers?: BorrowerOpportunity[];
    isLoading?: boolean;
  } | null;

  const data = marketplace?.borrowers ?? [];
  const isLoading = marketplace?.isLoading ?? false;

  const borrowers = useMemo(() => {
    return data.filter((b: BorrowerOpportunity) => {
      const matchesSearch =
        b.title.toLowerCase().includes(search.toLowerCase()) ||
        b.purpose.toLowerCase().includes(search.toLowerCase());

      const matchesFilter =
        filter === "All" ||
        (filter === "Low Risk" && b.aiRiskScore >= 85) ||
        (filter === "24M" && b.tenureMonths === 24) ||
        (filter === "36M" && b.tenureMonths === 36);

      return matchesSearch && matchesFilter;
    });
  }, [data, search, filter]);

  return (
    <DashboardLayout>
      <motion.div
        initial={{ opacity: 0, y: 18 }}
        animate={{ opacity: 1, y: 0 }}
      >
        <div className="mb-8">
          <p className="text-white/40">Lending Marketplace</p>
          <h1 className="text-5xl font-light text-white">
            Invest Intelligently
          </h1>
        </div>

        <div className="space-y-5">
          <SearchBar value={search} onChange={setSearch} />
          <FilterChips selected={filter} onSelect={setFilter} />

          <div className="grid gap-5 lg:grid-cols-2">
            {isLoading
              ? Array.from({ length: 4 }).map((_, i) => (
                  <Skeleton key={i} className="h-64" />
                ))
              : borrowers.map((b: BorrowerOpportunity) => (
                  <BorrowerCard key={b.id} borrower={b} />
                ))}
          </div>
        </div>
      </motion.div>
    </DashboardLayout>
  );
}