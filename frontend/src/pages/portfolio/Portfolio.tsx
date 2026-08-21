import { motion } from "framer-motion";

import DashboardLayout from "../../layouts/DashboardLayout";
import MetricCard from "../../components/portfolio/MetricCard";
import NetWorthChart from "../../components/portfolio/NetWorthChart";
import ActiveLoanCard from "../../components/portfolio/ActiveLoanCard";
import TransactionTimeline from "../../components/portfolio/TransactionTimeline";

import { usePortfolio } from "../../hooks/usePortfolio";
import Skeleton from "../../components/ui/Skeleton";

export default function Portfolio() {
  const { data, isLoading } = usePortfolio();

  return (
    <DashboardLayout>
      {/* Cinematic Background Glow */}
      <div className="pointer-events-none fixed inset-0 -z-10 overflow-hidden">
        <div className="absolute left-20 top-32 h-72 w-72 rounded-full bg-[#C7F5D9]/6 blur-[120px]" />
        <div className="absolute bottom-20 right-24 h-80 w-80 rounded-full bg-[#F6E7C8]/5 blur-[140px]" />
      </div>

      <motion.div
        initial={{ opacity: 0, y: 25 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, ease: "easeOut" }}
        className="space-y-8"
      >
        {/* Header */}
        <div>
          <p className="text-sm text-white/40">Wealth Center</p>

          <h1 className="mt-2 text-5xl font-light tracking-tight text-white">
            Portfolio
          </h1>
        </div>

        {/* Live Metric Cards */}
        <div className="grid gap-5 md:grid-cols-2 xl:grid-cols-4">
          {isLoading ? (
            <>
              <Skeleton className="h-32" />
              <Skeleton className="h-32" />
              <Skeleton className="h-32" />
              <Skeleton className="h-32" />
            </>
          ) : (
            <>
              <MetricCard
                label="Portfolio Value"
                value={`₹${(
                  data?.totalPortfolio ?? 0
                ).toLocaleString("en-IN")}`}
                accent="#F6E7C8"
              />

              <MetricCard
                label="Active Loans"
                value={String(data?.activeLoans ?? 0)}
              />

              <MetricCard
                label="Next EMI"
                value={`₹${(
                  data?.nextEmi ?? 0
                ).toLocaleString("en-IN")}`}
                accent="#F6E7C8"
              />

              <MetricCard
                label="Credit Score"
                value={String(data?.creditScore ?? 0)}
              />
            </>
          )}
        </div>

        {/* Net Worth Chart */}
        <NetWorthChart />

        {/* Active Loans */}
        <section>
          <div className="mb-5 flex items-center justify-between">
            <h2 className="text-2xl font-light text-white">
              Active Loans
            </h2>

            <span className="rounded-full border border-white/10 bg-white/5 px-3 py-1 text-xs text-white/50">
              {isLoading
                ? "Loading..."
                : `${data?.activeLoans ?? 0} ongoing`}
            </span>
          </div>

          <div className="flex gap-5 overflow-x-auto pb-3 scrollbar-width:none [-ms-overflow-style:none] [&::-webkit-scrollbar]:hidden">
            <ActiveLoanCard
              title="Home Expansion"
              principal={850000}
              emi={18420}
              progress={72}
              nextDue="28 Aug"
            />

            <ActiveLoanCard
              title="Higher Education"
              principal={320000}
              emi={9210}
              progress={41}
              nextDue="04 Sep"
            />

            <ActiveLoanCard
              title="Medical Support"
              principal={180000}
              emi={6100}
              progress={88}
              nextDue="15 Sep"
            />
          </div>
        </section>

        {/* Transaction Timeline */}
        <TransactionTimeline />
      </motion.div>
    </DashboardLayout>
  );
}