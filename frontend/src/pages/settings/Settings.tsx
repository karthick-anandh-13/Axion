import { useState } from "react";
import { motion } from "framer-motion";
import {
  Bell,
  CreditCard,
  Eye,
  Fingerprint,
  ShieldCheck,
} from "lucide-react";

import DashboardLayout from "../../layouts/DashboardLayout";
import GlassCard from "../../components/ui/GlassCard";
import { useSettings } from "../../hooks/useSettings";
import type { UserSettings } from "../../types/settings";

function Toggle({
  value,
  onChange,
}: {
  value: boolean;
  onChange: () => void;
}) {
  return (
    <motion.button
      onClick={onChange}
      whileTap={{ scale: 0.95 }}
      className={`relative h-7 w-14 rounded-full transition-colors ${
        value ? "bg-[#C7F5D9]" : "bg-white/15"
      }`}
    >
      <motion.div
        animate={{ x: value ? 28 : 2 }}
        transition={{
          type: "spring",
          stiffness: 400,
          damping: 28,
        }}
        className="absolute top-1 h-5 w-5 rounded-full bg-white shadow-lg"
      />
    </motion.button>
  );
}

export default function Settings() {
  const { data, mutation } = useSettings();

  const [overrides, setOverrides] = useState<Partial<UserSettings>>({});
  const settings = data ? { ...data, ...overrides } : null;

  if (!settings) {
    return (
      <DashboardLayout>
        <div className="flex h-[70vh] items-center justify-center">
          <div className="h-10 w-10 animate-spin rounded-full border-2 border-[#C7F5D9] border-t-transparent" />
        </div>
      </DashboardLayout>
    );
  }

  const toggle = (
    key: "biometric" | "autoPay" | "darkMode"
  ) => {
    const updated = {
      ...settings,
      [key]: !settings[key],
    };

    setOverrides((current) => ({ ...current, [key]: updated[key] }));
    mutation.mutate(updated);
  };

  const initials = settings.fullName
    .split(" ")
    .map((n) => n[0])
    .join("")
    .slice(0, 2);

  return (
    <DashboardLayout>
      <motion.div
        initial={{ opacity: 0, y: 24 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
      >
        {/* Header */}
        <div className="mb-8">
          <p className="text-white/40">Account</p>
          <h1 className="text-5xl font-light text-white">
            Settings
          </h1>
        </div>

        {/* Profile */}
        <div className="grid gap-6 lg:grid-cols-3">
          <GlassCard className="p-6 lg:col-span-1">
            <div className="relative">
              <div className="absolute -right-8 -top-8 h-28 w-28 rounded-full bg-[#C7F5D9]/10 blur-3xl" />

              <div className="flex flex-col items-center">
                <motion.div
                  whileHover={{ scale: 1.05, rotate: 6 }}
                  className="mb-5 flex h-24 w-24 items-center justify-center rounded-full bg-linear-to-br from-[#F6E7C8]/20 to-[#C7F5D9]/10 text-3xl font-light text-[#F6E7C8]"
                >
                  {initials}
                </motion.div>

                <h2 className="text-center text-2xl text-white">
                  {settings.fullName}
                </h2>

                <p className="mt-1 text-center text-white/40">
                  {settings.email}
                </p>

                <div className="mt-5 flex items-center gap-2 rounded-full bg-[#C7F5D9]/10 px-4 py-2 text-sm text-[#C7F5D9]">
                  <ShieldCheck size={16} />
                  {settings.kycVerified
                    ? "KYC Verified"
                    : "Verification Pending"}
                </div>
              </div>
            </div>
          </GlassCard>

          {/* Preferences */}
          <GlassCard className="p-6 lg:col-span-2">
            <h3 className="mb-6 text-xl text-white">
              Security & Preferences
            </h3>

            <div className="space-y-4">
              <div className="flex items-center justify-between rounded-2xl border border-white/8 bg-white/5 p-4">
                <div className="flex items-center gap-3">
                  <Fingerprint className="text-[#F6E7C8]" />
                  <div>
                    <p className="text-white">
                      Biometric Login
                    </p>
                    <p className="text-sm text-white/40">
                      Face ID & fingerprint
                    </p>
                  </div>
                </div>

                <Toggle
                  value={settings.biometric}
                  onChange={() => toggle("biometric")}
                />
              </div>

              <div className="flex items-center justify-between rounded-2xl border border-white/8 bg-white/5 p-4">
                <div className="flex items-center gap-3">
                  <Bell className="text-[#F6E7C8]" />
                  <div>
                    <p className="text-white">
                      Push Notifications
                    </p>
                    <p className="text-sm text-white/40">
                      AI & payment alerts
                    </p>
                  </div>
                </div>

                <Toggle
                  value={settings.autoPay}
                  onChange={() => toggle("autoPay")}
                />
              </div>

              <div className="flex items-center justify-between rounded-2xl border border-white/8 bg-white/5 p-4">
                <div className="flex items-center gap-3">
                  <Eye className="text-[#F6E7C8]" />
                  <div>
                    <p className="text-white">
                      Privacy Mode
                    </p>
                    <p className="text-sm text-white/40">
                      Hide balances in public
                    </p>
                  </div>
                </div>

                <Toggle
                  value={settings.darkMode}
                  onChange={() => toggle("darkMode")}
                />
              </div>
            </div>
          </GlassCard>
        </div>

        {/* Bottom Cards */}
        <div className="mt-6 grid gap-6 lg:grid-cols-2">
          <GlassCard className="p-6">
            <div className="mb-5 flex items-center gap-3">
              <CreditCard className="text-[#F6E7C8]" />
              <h3 className="text-xl text-white">
                Connected Banks
              </h3>
            </div>

            {["HDFC Bank", "State Bank of India"].map(
              (bank, i) => (
                <motion.div
                  key={bank}
                  initial={{ opacity: 0, x: 20 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ delay: i * 0.15 }}
                  className="mb-3 flex items-center justify-between rounded-xl bg-white/5 p-4"
                >
                  <div>
                    <p className="text-white">{bank}</p>
                    <p className="text-sm text-white/40">
                      **** **** {4821 + i}
                    </p>
                  </div>

                  <ShieldCheck className="text-[#C7F5D9]" />
                </motion.div>
              )
            )}
          </GlassCard>

          <GlassCard className="p-6">
            <h3 className="mb-5 text-xl text-white">
              Account Status
            </h3>

            {[
              [
                "KYC Verification",
                settings.kycVerified
                  ? "Completed"
                  : "Pending",
              ],
              ["Credit Profile", "Excellent"],
              ["Device Security", "Protected"],
            ].map(([label, value], i) => (
              <motion.div
                key={label}
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: i * 0.12 }}
                className="mb-3 flex justify-between rounded-xl bg-white/5 p-4"
              >
                <span className="text-white/60">
                  {label}
                </span>
                <span className="text-[#C7F5D9]">
                  {value}
                </span>
              </motion.div>
            ))}
          </GlassCard>
        </div>
      </motion.div>
    </DashboardLayout>
  );
}
