import DashboardLayout from "../../layouts/DashboardLayout";
import GlassCard from "../../components/ui/GlassCard";
import EmiRing from "../../components/repayment/EmiRing";
import PaymentCard from "../../components/repayment/PaymentCard";
import { useRepayment } from "../../hooks/useRepayment";

export default function Repayment() {
  const { data } = useRepayment();

  return (
    <DashboardLayout>
      <div className="mb-8">
        <p className="text-white/40">Payments</p>

        <h1 className="text-5xl font-light text-white">
          Repayment Center
        </h1>
      </div>

      <GlassCard className="p-8">
        <EmiRing
          progress={data?.progress ?? 62}
          remaining={data?.remainingAmount ?? 480000}
        />

        <div className="mt-6 text-center">
          <p className="text-white/40">
            Next EMI
          </p>

          <h2 className="mt-2 text-4xl font-light text-[#F6E7C8]">
            ₹
            {(data?.nextEmi ?? 18420).toLocaleString(
              "en-IN"
            )}
          </h2>
        </div>
      </GlassCard>

      <div className="mt-8 space-y-4">
        <h2 className="text-2xl font-light text-white">
          Upcoming Schedule
        </h2>

        {(data?.schedule ?? []).map((emi) => (
          <PaymentCard
            key={emi.id}
            date={emi.dueDate}
            amount={emi.amount}
            status={emi.status}
          />
        ))}
      </div>
    </DashboardLayout>
  );
}