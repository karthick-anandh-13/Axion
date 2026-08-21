import DashboardLayout from "../../layouts/DashboardLayout";
import GlassCard from "../../components/ui/GlassCard";
import CreditGauge from "../../components/ai/CreditGauge";
import FactorCard from "../../components/ai/FactorCard";
import { useAI } from "../../hooks/useAI";

export default function CreditIntelligence() {
  const { data } = useAI();

  return (
    <DashboardLayout>
      <div className="mb-8">
        <p className="text-white/40">
          Artificial Intelligence
        </p>

        <h1 className="text-5xl font-light text-white">
          Credit Intelligence
        </h1>
      </div>

      <GlassCard className="p-8">
        <CreditGauge
          score={data?.creditScore ?? 782}
        />

        <div className="mt-8 text-center">
          <h3 className="text-2xl text-white">
            {data?.approvalProbability ?? 94}%
            Approval Probability
          </h3>

          <p className="mt-2 text-white/45">
            AI believes this borrower has strong
            repayment potential.
          </p>
        </div>
      </GlassCard>

      <div className="mt-6 space-y-4">
        {(
          data?.factors ?? [
            {
              title: "Income Stability",
              impact: 24,
            },
            {
              title: "Repayment History",
              impact: 18,
            },
            { title: "Debt Ratio", impact: -9 },
          ]
        ).map((f) => (
          <FactorCard
            key={f.title}
            title={f.title}
            impact={f.impact}
          />
        ))}
      </div>
    </DashboardLayout>
  );
}