import DashboardLayout from "../../layouts/DashboardLayout";
import AdminMetric from "../../components/admin/AdminMetric";
import LoanApprovalCard from "../../components/admin/LoanApprovalCard";
import GlassCard from "../../components/ui/GlassCard";

import {
  Wallet,
  ShieldAlert,
  IndianRupee,
  Users,
} from "lucide-react";

export default function Admin() {
  return (
    <DashboardLayout>
      <div className="mb-8">
        <p className="text-white/40">Internal Operations</p>
        <h1 className="text-5xl font-light text-white">
          Command Center
        </h1>
      </div>

      <div className="grid gap-5 lg:grid-cols-4">
        <AdminMetric
          title="Pending Loans"
          value="24"
          icon={Wallet}
        />

        <AdminMetric
          title="Fraud Alerts"
          value="3"
          icon={ShieldAlert}
        />

        <AdminMetric
          title="Today's Revenue"
          value="₹4.8L"
          icon={IndianRupee}
        />

        <AdminMetric
          title="Active Users"
          value="8,421"
          icon={Users}
        />
      </div>

      <div className="mt-6 grid gap-6 lg:grid-cols-3">
        <div className="lg:col-span-2 space-y-4">
          <LoanApprovalCard
            name="Priya Sharma"
            amount="₹4,50,000"
            score={98}
          />

          <LoanApprovalCard
            name="Rahul Iyer"
            amount="₹7,00,000"
            score={91}
          />

          <LoanApprovalCard
            name="Meera Nair"
            amount="₹3,20,000"
            score={96}
          />
        </div>

        <GlassCard className="p-6">
          <h3 className="mb-5 text-xl text-white">
            Live Activity
          </h3>

          <div className="space-y-4 text-sm">
            <div>
              <p className="text-white">
                Loan approved
              </p>
              <p className="text-white/40">
                2 minutes ago
              </p>
            </div>

            <div>
              <p className="text-white">
                AI detected unusual repayment
              </p>
              <p className="text-white/40">
                9 minutes ago
              </p>
            </div>

            <div>
              <p className="text-white">
                New investor joined
              </p>
              <p className="text-white/40">
                18 minutes ago
              </p>
            </div>
          </div>
        </GlassCard>
      </div>
    </DashboardLayout>
  );
}