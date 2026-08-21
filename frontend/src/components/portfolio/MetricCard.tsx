import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";

interface Props {
  label: string;
  value: string;
  accent?: string;
}

export default function MetricCard({
  label,
  value,
  accent = "#C7F5D9",
}: Props) {
  return (
    <motion.div
      initial={{ opacity: 0, y: 18 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <GlassCard className="p-5">
        <p className="text-sm text-white/40">{label}</p>

        <h2
          className="mt-3 text-3xl font-light"
          style={{ color: accent }}
        >
          {value}
        </h2>
      </GlassCard>
    </motion.div>
  );
}