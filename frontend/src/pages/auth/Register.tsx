import { useMemo, useState } from "react";
import { Link } from "react-router-dom";
import AuthLayout from "../../layouts/AuthLayout";
import GlassCard from "../../components/ui/GlassCard";
import GlassInput from "../../components/ui/GlassInput";
import PrimaryButton from "../../components/ui/PrimaryButton";
import { useNavigate } from "react-router-dom";
import { register } from "../../api/auth";

export default function Register() {
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [accepted, setAccepted] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const strength = useMemo(() => {
    let score = 0;
    if (password.length >= 8) score++;
    if (/[A-Z]/.test(password)) score++;
    if (/[0-9]/.test(password)) score++;
    if (/[^A-Za-z0-9]/.test(password)) score++;
    return score;
  }, [password]);

  const strengthLabel = [
    "Very Weak",
    "Weak",
    "Fair",
    "Strong",
    "Excellent",
  ][strength];

  const passwordsMatch =
    confirmPassword.length > 0 && password === confirmPassword;

  const isFormValid =
    fullName &&
    email &&
    password &&
    confirmPassword &&
    passwordsMatch &&
    phoneNumber &&
    accepted;

  const handleRegister = async () => {
    if (!isFormValid || submitting) return;

    const nameParts = fullName.trim().split(/\s+/);
    const firstName = nameParts[0];
    const lastName = nameParts.slice(1).join(" ") || firstName;

    try {
      setSubmitting(true);
      setError("");
      await register({
        username: email.trim().toLowerCase(),
        email: email.trim().toLowerCase(),
        password,
        firstName,
        lastName,
        phoneNumber: phoneNumber.trim(),
      });
      navigate("/login");
    } catch {
      setError("We could not create your account. Check the details and try again.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <AuthLayout>
      <GlassCard className="w-full max-w-500px p-10">
        {/* Header */}
        <div className="mb-8 text-center">
          <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-full border border-white/10 bg-[#F6E7C8]/10 text-2xl">
            ✦
          </div>

          <h1 className="mt-6 text-4xl font-light text-white">
            Create your
          </h1>

          <h2 className="text-4xl font-light text-[#F6E7C8]">
            AXION ID
          </h2>

          <p className="mt-3 text-white/50">
            Begin your intelligent financial journey.
          </p>
        </div>

        {/* Form */}
        <div className="space-y-5">
          <GlassInput
            label="Full Name"
            value={fullName}
            onChange={setFullName}
          />

          <GlassInput
            label="Email Address"
            type="email"
            value={email}
            onChange={setEmail}
          />

          <GlassInput
            label="Phone Number"
            type="tel"
            value={phoneNumber}
            onChange={setPhoneNumber}
          />

          <GlassInput
            label="Password"
            type="password"
            value={password}
            onChange={setPassword}
          />

          {/* Password Strength */}
          <div>
            <div className="mb-2 flex items-center justify-between text-xs">
              <span className="text-white/40">Password Strength</span>
              <span className="text-[#C7F5D9]">{strengthLabel}</span>
            </div>

            <div className="flex gap-2">
              {[1, 2, 3, 4].map((i) => (
                <div
                  key={i}
                  className={`h-2 flex-1 rounded-full transition-all duration-300 ${
                    strength >= i ? "bg-[#C7F5D9]" : "bg-white/10"
                  }`}
                />
              ))}
            </div>
          </div>

          <GlassInput
            label="Confirm Password"
            type="password"
            value={confirmPassword}
            onChange={setConfirmPassword}
          />

          {confirmPassword.length > 0 && (
            <p
              className={`text-sm ${
                passwordsMatch ? "text-[#C7F5D9]" : "text-red-300"
              }`}
            >
              {passwordsMatch
                ? "✓ Passwords match"
                : "Passwords do not match"}
            </p>
          )}

          {error && <p className="text-sm text-red-300">{error}</p>}

          {/* Terms */}
          <label className="flex items-start gap-3 text-sm text-white/60">
            <input
              type="checkbox"
              checked={accepted}
              onChange={(e) => setAccepted(e.target.checked)}
              className="mt-1 accent-[#C7F5D9]"
            />

            <span>
              I agree to the{" "}
              <span className="text-[#F6E7C8]">Terms of Service</span> and{" "}
              <span className="text-[#F6E7C8]">Privacy Policy</span>.
            </span>
          </label>

          <PrimaryButton onClick={handleRegister} disabled={!isFormValid || submitting}>
            {submitting ? "Creating Account..." : "Create Account"}
          </PrimaryButton>
        </div>

        {/* Footer */}
        <div className="mt-8 text-center text-white/50">
          Already have an account?{" "}
          <Link
            to="/"
            className="text-[#F6E7C8] transition hover:text-white"
          >
            Sign In
          </Link>
        </div>
      </GlassCard>
    </AuthLayout>
  );
}
