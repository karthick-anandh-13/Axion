import DashboardLayout from "../../layouts/DashboardLayout";
import WelcomeHeader from "../../components/dashboard/WelcomeHeader";
import MetricCard from "../../components/dashboard/MetricCard";
import GlassCard from "../../components/ui/GlassCard";
import PortfolioChart from "../../components/dashboard/PortfolioChart";
import AIInsights from "../../components/dashboard/AIInsights";

export default function Home() {
  return (
    <DashboardLayout>
      <WelcomeHeader />

      <div className="grid gap-6 lg:grid-cols-4">
        <MetricCard
          title="Net Worth"
          value="₹12.4M"
          change="+18.4%"
        />

        <MetricCard
          title="Active Loans"
          value="18"
          change="+3 Today"
        />

        <MetricCard
          title="Monthly Return"
          value="12.8%"
          change="+2.1%"
        />

        <MetricCard
          title="AI Trust Score"
          value="98"
          change="Excellent"
        />
      </div>

      <div className="mt-6 grid gap-6 lg:grid-cols-3">
        <GlassCard className="lg:col-span-2 p-6">
          <h3 className="text-xl text-white mb-4">
            Portfolio Performance
          </h3>

          <div className="h-72 flex items-center justify-center text-white/30">
            Chart coming next →
          </div>
        </GlassCard>

        <GlassCard className="p-6">
          <h3 className="text-xl text-white">
            AI Insights
          </h3>

          <div className="mt-5 space-y-4">
            <div className="rounded-xl bg-white/5 p-4">
              <p className="text-sm text-white/40">
                Recommendation
              </p>
              <p className="mt-2 text-white">
                Increase lending allocation by 12%.
              </p>
            </div>

            <div className="rounded-xl bg-white/5 p-4">
              <p className="text-sm text-white/40">
                Risk Alert
              </p>
              <p className="mt-2 text-white">
                2 borrowers require review.
              </p>
            </div>
          </div>
        </GlassCard>
        <GlassCard className="lg:col-span-2 p-6">
            <PortfolioChart />
        </GlassCard>
        <div className="mt-6 grid gap-6 lg:grid-cols-3">
            <GlassCard className="lg:col-span-2 p-6">
                <PortfolioChart />
            </GlassCard>

            <AIInsights />
        </div>
      </div>
    </DashboardLayout>
  );
}