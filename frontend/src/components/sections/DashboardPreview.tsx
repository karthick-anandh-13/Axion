import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";
import {
  Brain,
  TrendingUp,
  ShieldCheck,
  ArrowUpRight,
} from "lucide-react";

export default function DashboardPreview() {
  return (
    <section className="relative py-32 px-6">
      <div className="mx-auto max-w-7xl">

        <motion.div
          initial={{ opacity: 0, y: 25 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          className="mb-14 text-center"
        >
          <p className="text-xs tracking-[0.3em] uppercase text-[#C7F5D9]">
            Intelligence
          </p>

          <h2 className="mt-3 text-5xl font-light text-white">
            Your finances,
            <span className="block text-[#F6E7C8]">
              visualized beautifully.
            </span>
          </h2>
        </motion.div>

        <div className="grid gap-6 lg:grid-cols-3">

          {/* Large Chart */}
          <GlassCard className="lg:col-span-2 p-7">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-white/50 text-sm">Net Worth</p>
                <h3 className="mt-2 text-4xl font-light text-[#F6E7C8]">
                  ₹12,48,920
                </h3>
              </div>

              <div className="rounded-full bg-emerald-300/10 px-4 py-2 text-emerald-200 text-sm">
                +18.4%
              </div>
            </div>

            <div className="mt-10 h-56">
              <svg viewBox="0 0 600 220" className="h-full w-full">
                <defs>
                  <linearGradient id="line" x1="0" y1="0" x2="1" y2="0">
                    <stop offset="0%" stopColor="#6EE7B7" />
                    <stop offset="100%" stopColor="#F6E7C8" />
                  </linearGradient>
                </defs>

                {[0,1,2,3].map(i=>(
                  <line
                    key={i}
                    x1="0"
                    y1={40+i*45}
                    x2="600"
                    y2={40+i*45}
                    stroke="rgba(255,255,255,0.05)"
                  />
                ))}

                <motion.path
                  initial={{ pathLength: 0 }}
                  whileInView={{ pathLength: 1 }}
                  viewport={{ once: true }}
                  transition={{ duration: 2 }}
                  d="M20 170 C90 140,140 155,200 100 S330 120,400 60 S520 80,580 35"
                  fill="none"
                  stroke="url(#line)"
                  strokeWidth="4"
                  strokeLinecap="round"
                />
              </svg>
            </div>
          </GlassCard>

          {/* AI Card */}
          <GlassCard className="p-7">
            <div className="flex h-14 w-14 items-center justify-center rounded-2xl bg-white/5">
              <Brain className="text-[#F6E7C8]" />
            </div>

            <h3 className="mt-6 text-2xl font-light text-white">
              AXION AI
            </h3>

            <p className="mt-3 text-white/60 leading-7">
              Repayment probability increased after salary verification.
            </p>

            <div className="mt-8 rounded-2xl bg-white/5 p-4">
              <p className="text-white/40 text-sm">Risk Score</p>
              <h4 className="mt-2 text-3xl text-[#C7F5D9] font-light">
                98
              </h4>
            </div>
          </GlassCard>

          {/* Small metrics */}
          <GlassCard className="p-6">
            <ShieldCheck className="text-[#C7F5D9]" />
            <p className="mt-4 text-white/50">Verified Loans</p>
            <h4 className="mt-2 text-3xl text-white">2,481</h4>
          </GlassCard>

          <GlassCard className="p-6">
            <TrendingUp className="text-[#F6E7C8]" />
            <p className="mt-4 text-white/50">Monthly Return</p>
            <h4 className="mt-2 text-3xl text-white">12.8%</h4>
          </GlassCard>

          <GlassCard className="p-6">
            <ArrowUpRight className="text-emerald-300" />
            <p className="mt-4 text-white/50">Capital Growth</p>
            <h4 className="mt-2 text-3xl text-white">+₹84K</h4>
          </GlassCard>

        </div>
      </div>
    </section>
  );
}