import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import AuthLayout from "../../layouts/AuthLayout";
import GlassCard from "../../components/ui/GlassCard";
import PrimaryButton from "../../components/ui/PrimaryButton";
import GlassInput from "../../components/ui/GlassInput";
import { login } from "../../api/auth";

export default function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    try {
      setLoading(true);

      await login({
        email,
        password,
      });

      navigate("/dashboard");
    } catch (error) {
      console.error("Login failed:", error);
      alert("Invalid email or password");
    } finally {
      setLoading(false);
    }
  };

  return (
    <AuthLayout>
      <GlassCard className="w-full max-w-md p-8">
        <div className="mb-8 text-center">
          <p className="text-sm tracking-[0.35em] text-[#C7F5D9] uppercase">
            Welcome
          </p>

          <h1 className="mt-3 text-4xl font-light text-white">Sign in to</h1>

          <h2 className="text-4xl font-light text-[#F6E7C8]">AXION</h2>
        </div>

        <div className="space-y-5">
          <GlassInput
            label="Email Address"
            type="email"
            value={email}
            onChange={setEmail}
          />

          <GlassInput
            label="Password"
            type="password"
            value={password}
            onChange={setPassword}
          />

          <div className="flex items-center justify-between text-sm">
            <label className="flex items-center gap-2 text-white/50">
              <input type="checkbox" className="accent-[#C7F5D9]" />
              Remember me
            </label>

            <Link
              to="/forgot"
              className="text-white/50 hover:text-[#F6E7C8]"
            >
              Forgot?
            </Link>
          </div>

          <div className="text-right">
            <Link
              to="/forgot"
              className="text-sm text-white/50 hover:text-[#F6E7C8]"
            >
              Forgot password?
            </Link>
          </div>

          <PrimaryButton onClick={handleLogin} disabled={loading}>
            {loading ? "Signing In..." : "Continue"}
          </PrimaryButton>
        </div>

        <div className="mt-8 text-center text-white/50">
          New to AXION?{" "}
          <Link to="/register" className="text-[#F6E7C8]">
            Create account
          </Link>
        </div>
      </GlassCard>
    </AuthLayout>
  );
}