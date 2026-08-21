import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  GraduationCap,
  Briefcase,
  Laptop,
  Building2,
} from "lucide-react";

import StepperLayout from "../../layouts/StepperLayout";
import GlassCard from "../../components/ui/GlassCard";
import PrimaryButton from "../../components/ui/PrimaryButton";

const options = [
  {
    id: "student",
    title: "Student",
    subtitle: "Learning & internships",
    icon: GraduationCap,
  },
  {
    id: "salaried",
    title: "Salaried",
    subtitle: "Full-time employee",
    icon: Briefcase,
  },
  {
    id: "self",
    title: "Self Employed",
    subtitle: "Freelancer / Consultant",
    icon: Laptop,
  },
  {
    id: "business",
    title: "Business Owner",
    subtitle: "Founder or entrepreneur",
    icon: Building2,
  },
];

export default function Employment() {
  const navigate = useNavigate();
  const [selected, setSelected] = useState("");

  return (
    <StepperLayout
      step={3}
      total={7}
      title="How do you earn?"
      subtitle="Choose the option that best describes you."
    >
      <div className="grid gap-4 md:grid-cols-2">
        {options.map((item) => {
          const Icon = item.icon;
          const active = selected === item.id;

          return (
            <button
              key={item.id}
              onClick={() => setSelected(item.id)}
              className="text-left"
            >
              <GlassCard
                hover
                className={`p-5 transition-all duration-300 ${
                  active
                    ? "border-[#C7F5D9]/50 bg-[#C7F5D9]/10"
                    : "border-white/10"
                }`}
              >
                <div className="mb-4 flex h-12 w-12 items-center justify-center rounded-2xl bg-white/5">
                  <Icon
                    className={
                      active ? "text-[#C7F5D9]" : "text-[#F6E7C8]"
                    }
                    size={24}
                  />
                </div>

                <h3 className="text-lg text-white">{item.title}</h3>

                <p className="mt-2 text-sm text-white/50">
                  {item.subtitle}
                </p>
              </GlassCard>
            </button>
          );
        })}
      </div>

      <div className="mt-8">
        <div
          onClick={() =>
            selected && navigate("/onboarding/income")
          }
        >
          <PrimaryButton>
            {selected ? "Continue" : "Select an option"}
          </PrimaryButton>
        </div>
      </div>
    </StepperLayout>
  );
}