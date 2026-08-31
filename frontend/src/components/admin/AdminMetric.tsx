import GlassCard from "../ui/GlassCard";
import type { LucideIcon } from "lucide-react";
import { motion } from "framer-motion";

interface Props {
  title: string;
  value: string;
  icon: LucideIcon;
}

export default function AdminMetric({
  title,
  value,
  icon: Icon,
}: Props) {
  return (
    <motion.div whileHover={{ y: -4 }}>
      <GlassCard className="p-5">
        <div className="flex items-center justify-between">
          <Icon className="text-[#F6E7C8]" size={22} />
          <span className="text-xs text-white/30">Today</span>
        </div>

        <h2 className="mt-4 text-3xl font-light text-[#F6E7C8]">
          {value}
        </h2>

        <p className="mt-2 text-sm text-white/50">{title}</p>
      </GlassCard>
    </motion.div>
  );
}
