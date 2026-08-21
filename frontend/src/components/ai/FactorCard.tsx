import { motion } from "framer-motion";

interface Props {
  title: string;
  impact: number;
}

export default function FactorCard({
  title,
  impact,
}: Props) {
  const positive = impact >= 0;

  return (
    <motion.div
      whileHover={{ x: 4 }}
      className="rounded-2xl border border-white/8 bg-white/5 p-4"
    >
      <div className="flex items-center justify-between">
        <p className="text-white">{title}</p>

        <span
          className={`text-sm ${
            positive
              ? "text-[#C7F5D9]"
              : "text-[#F6E7C8]"
          }`}
        >
          {positive ? "+" : ""}
          {impact}
        </span>
      </div>

      <div className="mt-3 h-2 rounded-full bg-white/10">
        <motion.div
          initial={{ width: 0 }}
          animate={{
            width: `${Math.min(
              100,
              Math.abs(impact) * 4
            )}%`,
          }}
          transition={{ duration: 1 }}
          className={`h-full rounded-full ${
            positive
              ? "bg-[#C7F5D9]"
              : "bg-[#F6E7C8]"
          }`}
        />
      </div>
    </motion.div>
  );
}