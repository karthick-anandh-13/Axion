import { useEffect, useRef, useState } from "react";
import { ArrowLeft } from "lucide-react";
import { Link } from "react-router-dom";
import AuthLayout from "../../layouts/AuthLayout";
import GlassCard from "../../components/ui/GlassCard";
import PrimaryButton from "../../components/ui/PrimaryButton";

export default function VerifyOTP() {
  const [otp, setOtp] = useState(["", "", "", "", "", ""]);
  const [seconds, setSeconds] = useState(30);

  const refs = useRef<(HTMLInputElement | null)[]>([]);
  useEffect(() => {
    if (seconds === 0) return;

    const timer = setInterval(() => {
      setSeconds((s) => s - 1);
    }, 1000);

    return () => clearInterval(timer);
  }, [seconds]);

  const handleChange = (value: string, index: number) => {
    if (!/^[0-9]?$/.test(value)) return;

    const next = [...otp];
    next[index] = value;
    setOtp(next);

    if (value && index < 5) refs.current[index + 1]?.focus();
  };

  const handleBackspace = (
    e: React.KeyboardEvent<HTMLInputElement>,
    index: number
  ) => {
    if (e.key === "Backspace" && !otp[index] && index > 0) {
      refs.current[index - 1]?.focus();
    }
  };

  const resend = () => {
    setSeconds(30);
    setOtp(["", "", "", "", "", ""]);
    refs.current[0]?.focus();
  };

  return (
    <AuthLayout>
      <GlassCard className="w-full max-w-460px p-10">
        <Link
          to="/register"
          className="mb-6 inline-flex items-center gap-2 text-white/50 hover:text-[#F6E7C8]"
        >
          <ArrowLeft size={18} />
          Back
        </Link>

        <div className="mb-10 text-center">
          <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-full border border-white/10 bg-[#F6E7C8]/10">
            ✦
          </div>

          <h1 className="mt-6 text-3xl font-light text-white">
            Verify your identity
          </h1>

          <p className="mt-3 text-white/50">
            Enter the 6-digit code sent to your email.
          </p>
        </div>

        <div className="mb-8 flex justify-between gap-3">
          {otp.map((digit, index) => (
            <input
              key={index}
              ref={(el) => {
                refs.current[index] = el;
              }}
              value={digit}
              onChange={(e) => handleChange(e.target.value, index)}
              onKeyDown={(e) => handleBackspace(e, index)}
              maxLength={1}
              className="h-16 w-14 rounded-2xl border border-white/10 bg-white/5 text-center text-2xl text-white outline-none backdrop-blur-xl transition focus:border-[#C7F5D9] focus:shadow-[0_0_20px_rgba(199,245,217,0.15)]"
            />
          ))}
        </div>

        <PrimaryButton>Verify Account</PrimaryButton>

        <div className="mt-8 text-center">
          {seconds > 0 ? (
            <p className="text-white/40">
              Resend code in{" "}
              <span className="text-[#F6E7C8]">{seconds}s</span>
            </p>
          ) : (
            <button
              onClick={resend}
              className="text-[#F6E7C8] hover:underline"
            >
              Resend OTP
            </button>
          )}
        </div>
      </GlassCard>
    </AuthLayout>
  );
}