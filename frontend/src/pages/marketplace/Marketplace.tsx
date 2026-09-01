import { useMemo, useState } from "react";
import { motion } from "framer-motion";

import DashboardLayout from "../../layouts/DashboardLayout";
import SearchBar from "../../components/marketplace/SearchBar";
import FilterChips from "../../components/marketplace/FilterChips";
import BorrowerCard from "../../components/marketplace/BorrowerCard";
import type { BorrowerOpportunity } from "../../types/marketplace";

const DEMO_DATA: BorrowerOpportunity[] = [
  {
    id: "1",
    title: "Medical Emergency Loan",
    purpose: "MEDICAL",
    amount: 250000,
    tenureMonths: 24,
    interestRate: 11.5,
    aiRiskScore: 94,
  } as BorrowerOpportunity,
  {
    id: "2",
    title: "Business Expansion",
    purpose: "BUSINESS",
    amount: 850000,
    tenureMonths: 36,
    interestRate: 13.8,
    aiRiskScore: 82,
  } as BorrowerOpportunity,
  {
    id: "3",
    title: "Higher Education",
    purpose: "EDUCATION",
    amount: 420000,
    tenureMonths: 24,
    interestRate: 10.9,
    aiRiskScore: 96,
  } as BorrowerOpportunity,
  {
    id: "4",
    title: "Home Renovation",
    purpose: "HOME_REPAIR",
    amount: 180000,
    tenureMonths: 12,
    interestRate: 11.2,
    aiRiskScore: 91,
  } as BorrowerOpportunity,
  {
    id: "5",
    title: "Vehicle Purchase",
    purpose: "VEHICLE",
    amount: 600000,
    tenureMonths: 36,
    interestRate: 12.7,
    aiRiskScore: 86,
  } as BorrowerOpportunity,
  {
    id: "6",
    title: "Agriculture Equipment",
    purpose: "AGRICULTURE",
    amount: 320000,
    tenureMonths: 24,
    interestRate: 11.8,
    aiRiskScore: 89,
  } as BorrowerOpportunity,
  {
    id: "7",
    title: "Startup Capital",
    purpose: "BUSINESS",
    amount: 1200000,
    tenureMonths: 60,
    interestRate: 15.2,
    aiRiskScore: 78,
  } as BorrowerOpportunity,
  {
    id: "8",
    title: "Debt Consolidation",
    purpose: "DEBT_CONSOLIDATION",
    amount: 540000,
    tenureMonths: 36,
    interestRate: 12.3,
    aiRiskScore: 88,
  } as BorrowerOpportunity,
  {
    id: "9",
    title: "Emergency Family Support",
    purpose: "EMERGENCY",
    amount: 150000,
    tenureMonths: 12,
    interestRate: 10.7,
    aiRiskScore: 97,
  } as BorrowerOpportunity,
  {
    id: "10",
    title: "Medical Surgery",
    purpose: "MEDICAL",
    amount: 700000,
    tenureMonths: 24,
    interestRate: 11.9,
    aiRiskScore: 90,
  } as BorrowerOpportunity,
  {
    id: "11",
    title: "Organic Farming",
    purpose: "AGRICULTURE",
    amount: 380000,
    tenureMonths: 24,
    interestRate: 11.1,
    aiRiskScore: 92,
  } as BorrowerOpportunity,
  {
    id: "12",
    title: "University Tuition",
    purpose: "EDUCATION",
    amount: 900000,
    tenureMonths: 48,
    interestRate: 12.6,
    aiRiskScore: 84,
  } as BorrowerOpportunity,
];

const ITEMS_PER_PAGE = 6;

export default function Marketplace() {
  const [search, setSearch] = useState("");
  const [filter, setFilter] = useState("All");
  const [page, setPage] = useState(1);

  const filtered = useMemo(() => {
    return DEMO_DATA.filter((b) => {
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
  }, [search, filter]);

  const totalPages = Math.ceil(filtered.length / ITEMS_PER_PAGE);

  const borrowers = useMemo(() => {
    const start = (page - 1) * ITEMS_PER_PAGE;
    return filtered.slice(start, start + ITEMS_PER_PAGE);
  }, [filtered, page]);

  const changeFilter = (value: string) => {
    setFilter(value);
    setPage(1);
  };

  const changeSearch = (value: string) => {
    setSearch(value);
    setPage(1);
  };

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
          <SearchBar value={search} onChange={changeSearch} />
          <FilterChips selected={filter} onSelect={changeFilter} />

          <div className="grid gap-5 lg:grid-cols-2">
            {borrowers.map((b) => (
              <BorrowerCard key={b.id} borrower={b} />
            ))}
          </div>

          {/* Pagination */}
          <div className="flex flex-col items-center gap-4 pt-6">
            <p className="text-sm text-white/40">
              Showing {(page - 1) * ITEMS_PER_PAGE + 1}–
              {Math.min(page * ITEMS_PER_PAGE, filtered.length)} of{" "}
              {filtered.length} opportunities
            </p>

            <div className="flex items-center gap-2">
              <button
                onClick={() => setPage((p) => Math.max(1, p - 1))}
                disabled={page === 1}
                className="rounded-lg border border-white/10 px-3 py-2 text-white/60 disabled:opacity-30"
              >
                ‹
              </button>

              {Array.from({ length: totalPages }, (_, i) => i + 1).map(
                (num) => (
                  <button
                    key={num}
                    onClick={() => setPage(num)}
                    className={`h-10 w-10 rounded-lg transition ${
                      page === num
                        ? "bg-[#C7F5D9] text-black"
                        : "border border-white/10 bg-white/5 text-white"
                    }`}
                  >
                    {num}
                  </button>
                )
              )}

              <button
                onClick={() =>
                  setPage((p) => Math.min(totalPages, p + 1))
                }
                disabled={page === totalPages}
                className="rounded-lg border border-white/10 px-3 py-2 text-white/60 disabled:opacity-30"
              >
                ›
              </button>
            </div>
          </div>
        </div>
      </motion.div>
    </DashboardLayout>
  );
}