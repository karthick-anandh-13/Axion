import { useNavigate } from "react-router-dom";
import { Sparkles } from "lucide-react";

import StepperLayout from "../../layouts/StepperLayout";
import PrimaryButton from "../../components/ui/PrimaryButton";

export default function Welcome() {
  const navigate = useNavigate();

  return (
    <StepperLayout
      step={1}
      total={7}
      title="Let's build your financial identity"
      subtitle="This takes less than 3 minutes."
    >
      <div className="space-y-6">

        <div className="rounded-3xl border border-white/10 bg-white/5 p-6">
          <div className="mb-4 flex h-14 w-14 items-center justify-center rounded-2xl bg-[#F6E7C8]/10">
            <Sparkles className="text-[#F6E7C8]" />
          </div>

          <h2 className="text-xl text-white">
            AXION AI will personalize everything for you
          </h2>

          <p className="mt-3 leading-7 text-white/60">
            Interest rates, lending opportunities, risk analysis and your
            financial dashboard will be tailored to your profile.
          </p>
        </div>

        <PrimaryButton>
          <span onClick={() => navigate("/onboarding/personal")}>
            Begin Setup
          </span>
        </PrimaryButton>

      </div>
    </StepperLayout>
  );
}