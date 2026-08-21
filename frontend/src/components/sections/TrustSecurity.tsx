import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";
import { ShieldCheck, Fingerprint, LockKeyhole, ScanFace } from "lucide-react";

const items = [
  {
    icon: ShieldCheck,
    title: "Bank-grade Encryption",
    desc: "Every transaction is protected with enterprise-level security.",
  },
  {
    icon: Fingerprint,
    title: "Biometric Authentication",
    desc: "Passwordless identity verification across devices.",
  },
  {
    icon: LockKeyhole,
    title: "Zero Trust Architecture",
    desc: "Continuous verification instead of one-time authentication.",
  },
  {
    icon: ScanFace,
    title: "AI Fraud Detection",
    desc: "Real-time behavioral analysis prevents suspicious activity.",
  },
];

export default function TrustSecurity() {
  return (
    <section className="py-32 px-6">
      <div className="mx-auto max-w-7xl">
        <motion.div
          initial={{ opacity:0, y:20 }}
          whileInView={{ opacity:1, y:0 }}
          viewport={{ once:true }}
          className="mb-14 text-center"
        >
          <p className="text-xs tracking-[0.35em] uppercase text-[#C7F5D9]">
            Trust
          </p>

          <h2 className="mt-3 text-5xl font-light text-white">
            Security designed as
            <span className="block text-[#F6E7C8]">
              a first-class experience.
            </span>
          </h2>
        </motion.div>

        <div className="grid gap-6 md:grid-cols-2">
          {items.map((item, i) => {
            const Icon = item.icon;

            return (
              <motion.div
                key={item.title}
                initial={{ opacity:0, y:30 }}
                whileInView={{ opacity:1, y:0 }}
                viewport={{ once:true }}
                transition={{ delay:i*0.12 }}
              >
                <GlassCard className="p-7">
                  <div className="flex items-start gap-5">
                    <div className="flex h-14 w-14 items-center justify-center rounded-2xl bg-[#F6E7C8]/10">
                      <Icon className="text-[#F6E7C8]" size={24}/>
                    </div>

                    <div>
                      <h3 className="text-xl text-white">{item.title}</h3>
                      <p className="mt-2 leading-7 text-white/60">
                        {item.desc}
                      </p>
                    </div>
                  </div>
                </GlassCard>
              </motion.div>
            );
          })}
        </div>
      </div>
    </section>
  );
}