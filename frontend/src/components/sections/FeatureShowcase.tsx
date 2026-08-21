import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";
import { Wallet, TrendingUp, Brain } from "lucide-react";

const features = [
  {
    icon: Wallet,
    title: "Borrow",
    subtitle: "AI-powered personal lending",
    description:
      "Receive fair loan offers based on your financial profile within minutes.",
  },
  {
    icon: TrendingUp,
    title: "Lend",
    subtitle: "Grow capital intelligently",
    description:
      "Fund verified borrowers and track returns through transparent analytics.",
  },
  {
    icon: Brain,
    title: "AXION AI",
    subtitle: "Predict • Analyze • Protect",
    description:
      "Our intelligence engine evaluates repayment behavior and financial risk.",
  },
];

export default function FeatureShowcase() {
  return (
    <section className="relative py-32 px-6">
      <div className="mx-auto max-w-7xl">
        <motion.div
          initial={{ opacity: 0, y: 25 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          transition={{ duration: 0.6 }}
          className="mb-16 text-center"
        >
          <p className="mb-3 tracking-[0.3em] text-[#C7F5D9] text-xs uppercase">
            Experience
          </p>

          <h2 className="text-5xl font-light text-white">
            Three products.
            <span className="block text-[#F6E7C8]">
              One intelligent ecosystem.
            </span>
          </h2>
        </motion.div>

        <div className="grid gap-8 md:grid-cols-3">
          {features.map((item, index) => {
            const Icon = item.icon;

            return (
              <motion.div
                key={item.title}
                initial={{ opacity: 0, y: 40 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ delay: index * 0.15, duration: 0.6 }}
              >
                <GlassCard className="h-full p-7">
                  <div className="mb-8 flex h-14 w-14 items-center justify-center rounded-2xl border border-white/10 bg-white/5">
                    <Icon className="text-[#F6E7C8]" size={26} />
                  </div>

                  <h3 className="text-2xl font-light text-white">
                    {item.title}
                  </h3>

                  <p className="mt-2 text-[#C7F5D9] text-sm">
                    {item.subtitle}
                  </p>

                  <p className="mt-6 leading-7 text-white/60">
                    {item.description}
                  </p>

                  <div className="mt-10 h-px bg-gradient-to-r from-[#C7F5D9]/40 to-transparent" />

                  <button className="mt-5 text-[#F6E7C8] text-sm hover:translate-x-1 transition">
                    Learn more →
                  </button>
                </GlassCard>
              </motion.div>
            );
          })}
        </div>
      </div>
    </section>
  );
}