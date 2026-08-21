import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { IndianRupee } from "lucide-react";

import StepperLayout from "../../layouts/StepperLayout";
import GlassCard from "../../components/ui/GlassCard";
import PrimaryButton from "../../components/ui/PrimaryButton";

export default function Income() {
  const navigate = useNavigate();

  const [income, setIncome] = useState(50000);

  const formatted = new Intl.NumberFormat("en-IN").format(income);

  const range =
    income < 30000
      ? "Starter"
      : income < 70000
      ? "Growing"
      : income < 150000
      ? "Professional"
      : "Executive";

  return (
    <StepperLayout
      step={4}
      total={7}
      title="Monthly income"
      subtitle="This helps AXION personalize lending and investment opportunities."
    >
      <div className="space-y-6">

        <GlassCard className="p-8 text-center" hover={false}>
          <div className="mx-auto mb-5 flex h-14 w-14 items-center justify-center rounded-full bg-[#F6E7C8]/10">
            <IndianRupee className="text-[#F6E7C8]" />
          </div>

          <p className="text-sm uppercase tracking-[0.25em] text-white/40">
            Monthly Income
          </p>

          <h2 className="mt-4 text-5xl font-light text-[#F6E7C8]">
            ₹{formatted}
          </h2>

          <div className="mt-4 inline-flex rounded-full bg-[#C7F5D9]/10 px-4 py-2 text-sm text-[#C7F5D9]">
            {range}
          </div>
        </GlassCard>

        <div className="px-2">
          <input
            type="range"
            min={10000}
            max={300000}
            step={1000}
            value={income}
            onChange={(e) => setIncome(Number(e.target.value))}
            className="h-2 w-full cursor-pointer appearance-none rounded-full bg-white/10 accent-[#C7F5D9]"
          />

          <div className="mt-3 flex justify-between text-xs text-white/40">
            <span>₹10K</span>
            <span>₹3L+</span>
          </div>
        </div>

        <div className="grid grid-cols-2 gap-4">
          {[
            25000,
            50000,
            100000,
            200000,
          ].map((value) => (
            <button
              key={value}
              onClick={() => setIncome(value)}
              className={`rounded-2xl border py-4 transition-all ${
                income === value
                  ? "border-[#C7F5D9]/60 bg-[#C7F5D9]/10 text-[#C7F5D9]"
                  : "border-white/10 bg-white/5 text-white/60"
              }`}
            >
              ₹{new Intl.NumberFormat("en-IN").format(value)}
            </button>
          ))}
        </div>

        <PrimaryButton
          onClick={() => navigate("/onboarding/goals")}
        >
          Continue
        </PrimaryButton>

      </div>
    </StepperLayout>
  );
}