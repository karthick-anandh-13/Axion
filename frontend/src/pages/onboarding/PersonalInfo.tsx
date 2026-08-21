import { useNavigate } from "react-router-dom";
import { useState } from "react";

import StepperLayout from "../../layouts/StepperLayout";
import GlassInput from "../../components/ui/GlassInput";
import PrimaryButton from "../../components/ui/PrimaryButton";

export default function PersonalInfo() {
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [dob, setDob] = useState("");
  const [phone, setPhone] = useState("");

  return (
    <StepperLayout
      step={2}
      total={7}
      title="Personal information"
      subtitle="Tell us a little about yourself."
    >
      <div className="space-y-5">

        <GlassInput
          label="Full Name"
          value={name}
          onChange={setName}
        />

        <GlassInput
          label="Date of Birth"
          value={dob}
          onChange={setDob}
        />

        <GlassInput
          label="Phone Number"
          value={phone}
          onChange={setPhone}
        />

        <div className="pt-4">
          <div onClick={() => navigate("/onboarding/employment")}>
            <PrimaryButton>Continue</PrimaryButton>
          </div>
        </div>

      </div>
    </StepperLayout>
  );
}