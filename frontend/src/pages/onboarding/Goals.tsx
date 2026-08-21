import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Home,
  Wallet,
  GraduationCap,
  BriefcaseBusiness,
  Shield,
} from "lucide-react";

import StepperLayout from "../../layouts/StepperLayout";
import GlassCard from "../../components/ui/GlassCard";
import PrimaryButton from "../../components/ui/PrimaryButton";

const goals = [
  {
    id: "wealth",
    title: "Build Wealth",
    icon: Wallet,
  },
  {
    id: "home",
    title: "Buy a Home",
    icon: Home,
  },
  {
    id: "education",
    title: "Education",
    icon: GraduationCap,
  },
  {
    id: "business",
    title: "Start Business",
    icon: BriefcaseBusiness,
  },
  {
    id: "security",
    title: "Emergency Fund",
    icon: Shield,
  },
];

export default function Goals() {
  const navigate = useNavigate();
  const [selected, setSelected] = useState<string[]>([]);

  const toggle = (id: string) => {
    setSelected((prev) =>
      prev.includes(id)
        ? prev.filter((x) => x !== id)
        : [...prev, id]
    );
  };

  return (
    <StepperLayout
      step={5}
      total={7}
      title="What are your goals?"
      subtitle="Choose one or more. AXION will personalize your experience."
    >
      <div className="grid gap-4 sm:grid-cols-2">
        {goals.map((goal) => {
          const Icon = goal.icon;
          const active = selected.includes(goal.id);

          return (
            <button
              key={goal.id}
              onClick={() => toggle(goal.id)}
              className="text-left"
            >
              <GlassCard
                className={`p-5 transition-all duration-300 ${
                  active
                    ? "border-[#C7F5D9]/50 bg-[#C7F5D9]/10"
                    : ""
                }`}
              >
                <div className="flex items-center justify-between">
                  <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-white/5">
                    <Icon
                      size={22}
                      className={
                        active
                          ? "text-[#C7F5D9]"
                          : "text-[#F6E7C8]"
                      }
                    />
                  </div>

                  {active && (
                    <div className="h-6 w-6 rounded-full bg-[#C7F5D9] text-center text-sm font-bold text-black">
                      ✓
                    </div>
                  )}
                </div>

                <h3 className="mt-5 text-lg text-white">
                  {goal.title}
                </h3>
              </GlassCard>
            </button>
          );
        })}
      </div>

      <div className="mt-8">
        <PrimaryButton
          disabled={selected.length === 0}
          onClick={() => navigate("/onboarding/kyc")}
        >
          Continue
        </PrimaryButton>
      </div>
    </StepperLayout>
  );
}