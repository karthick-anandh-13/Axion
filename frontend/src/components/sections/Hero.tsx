
import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";
import { ArrowRight, Sparkles } from "lucide-react";

export default function Hero() {
  return (
    <section className="relative flex min-h-screen items-center justify-center px-6">
      <div className="mx-auto grid max-w-7xl gap-12 lg:grid-cols-2 lg:items-center">

        {/* Left Side */}
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8 }}
        >
          <div className="mb-6 inline-flex items-center gap-2 rounded-full border border-white/10 bg-white/5 px-4 py-2 backdrop-blur-xl">
            <Sparkles size={14} className="text-[#C7F5D9]" />
            <span className="text-xs tracking-[0.2em] text-[#C7F5D9] uppercase">
              AI Powered Finance
            </span>
          </div>

          <h1 className="text-6xl font-light leading-tight text-white lg:text-7xl">
            Money,
            <br />
            <span className="text-[#F6E7C8]">
              Reimagined.
            </span>
          </h1>

          <p className="mt-8 max-w-xl text-lg leading-8 text-white/60">
            Borrow smarter, lend confidently, and let AI help you make
            financial decisions with clarity.
          </p>

          <div className="mt-10 flex gap-4">
            <motion.button
              whileTap={{ scale: 0.96 }}
              whileHover={{ scale: 1.03 }}
              className="flex items-center gap-2 rounded-full bg-[#F6E7C8] px-7 py-4 text-black font-medium"
            >
              Get Started
              <ArrowRight size={18} />
            </motion.button>

            <button className="rounded-full border border-white/10 bg-white/5 px-7 py-4 text-white backdrop-blur-xl hover:bg-white/10">
              Explore
            </button>
          </div>
        </motion.div>

        {/* Right Side */}
        <motion.div
          initial={{ opacity: 0, rotate: 5 }}
          animate={{ opacity: 1, rotate: 0 }}
          transition={{ duration: 1 }}
        >
          <GlassCard className="p-7">
            <p className="text-sm tracking-[0.25em] text-white/50">
              TOTAL CAPITAL
            </p>

            <h2 className="mt-4 text-5xl font-light text-[#F6E7C8]">
              ₹12.4M
            </h2>

            <div className="mt-8 space-y-4">
              <div className="flex items-center justify-between">
                <span className="text-white/50">AI Trust Score</span>
                <span className="text-[#C7F5D9]">98%</span>
              </div>

              <div className="h-2 overflow-hidden rounded-full bg-white/10">
                <motion.div
                  initial={{ width: 0 }}
                  animate={{ width: "98%" }}
                  transition={{ duration: 1.4 }}
                  className="h-full rounded-full bg-[#C7F5D9]"
                />
              </div>

              <div className="mt-6 rounded-2xl bg-white/5 p-4">
                <p className="text-sm text-white/40">Today's Insight</p>
                <p className="mt-2 text-white">
                  Your lending profile is stronger than 91% of users.
                </p>
              </div>
            </div>
          </GlassCard>
        </motion.div>

      </div>
    </section>
  );
}