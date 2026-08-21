import { ReactNode } from "react";
import { motion } from "framer-motion";
import CinematicBackground from "../components/background/CinematicBackground";
import GlassCard from "../components/ui/GlassCard";

interface Props {
  children: ReactNode;
  step: number;
  total: number;
  title: string;
  subtitle: string;
}

export default function StepperLayout({
  children,
  step,
  total,
  title,
  subtitle,
}: Props) {
  const progress = (step / total) * 100;

  return (
    <>
      <CinematicBackground />

      <main className="relative z-10 flex min-h-screen items-center justify-center px-6 py-12">
        <GlassCard className="w-full max-w-2xl p-10">

          {/* Progress */}
          <div className="mb-8">
            <div className="mb-3 flex justify-between text-sm text-white/40">
              <span>Step {step}</span>
              <span>{total}</span>
            </div>

            <div className="h-2 overflow-hidden rounded-full bg-white/10">
              <motion.div
                initial={{ width: 0 }}
                animate={{ width: `${progress}%` }}
                transition={{ duration: 0.6 }}
                className="h-full rounded-full bg-gradient-to-r from-[#C7F5D9] to-[#F6E7C8]"
              />
            </div>
          </div>

          {/* Heading */}
          <div className="mb-10 text-center">
            <h1 className="text-4xl font-light text-white">
              {title}
            </h1>

            <p className="mt-3 text-white/50">
              {subtitle}
            </p>
          </div>

          {children}

        </GlassCard>
      </main>
    </>
  );
}