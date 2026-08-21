import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import StepperLayout from "../../layouts/StepperLayout";
import GlassCard from "../../components/ui/GlassCard";

const messages = [
  "Analyzing financial profile...",
  "Evaluating repayment behavior...",
  "Calculating AI trust score...",
  "Generating personalized dashboard...",
];

export default function AIProfile() {
  const navigate = useNavigate();

  const [index, setIndex] = useState(0);

  useEffect(() => {
    if (index === messages.length - 1) {
      const done = setTimeout(
        () => navigate("/dashboard"),
        1800
      );
      return () => clearTimeout(done);
    }

    const timer = setTimeout(() => {
      setIndex((i) => i + 1);
    }, 1800);

    return () => clearTimeout(timer);
  }, [index, navigate]);

  return (
    <StepperLayout
      step={7}
      total={7}
      title="AXION AI is building your profile"
      subtitle="Please wait a few seconds."
    >
      <div className="space-y-8">
        <div className="flex justify-center">
          <div className="relative h-32 w-32">
            <div className="absolute inset-0 animate-ping rounded-full bg-[#C7F5D9]/20" />

            <div className="absolute inset-3 rounded-full border border-[#C7F5D9]/40" />

            <div className="absolute inset-6 flex items-center justify-center rounded-full bg-[#F6E7C8]/10 text-4xl">
              ✦
            </div>
          </div>
        </div>

        <GlassCard hover={false} className="p-6">
          <p className="text-center text-lg text-white">
            {messages[index]}
          </p>

          <div className="mt-6 h-2 overflow-hidden rounded-full bg-white/10">
            <div
              style={{
                width: `${((index + 1) / messages.length) * 100}%`,
              }}
              className="h-full rounded-full bg-gradient-to-r from-[#C7F5D9] to-[#F6E7C8] transition-all duration-700"
            />
          </div>
        </GlassCard>
      </div>
    </StepperLayout>
  );
}