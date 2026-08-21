import { motion } from "framer-motion";

interface Props {
  title: string;
  subtitle?: string;

  enabled: boolean;

  onToggle: () => void;
}

export default function SettingRow({
  title,
  subtitle,
  enabled,
  onToggle,
}: Props) {
  return (
    <div className="flex items-center justify-between rounded-2xl border border-white/8 bg-white/5 p-4">
      <div>
        <h3 className="text-white">{title}</h3>

        {subtitle && (
          <p className="text-sm text-white/40">{subtitle}</p>
        )}
      </div>

      <motion.button
        whileTap={{ scale: 0.9 }}
        onClick={onToggle}
        className={`flex h-7 w-12 items-center rounded-full p-1 transition ${
          enabled ? "bg-[#C7F5D9]" : "bg-white/15"
        }`}
      >
        <motion.div
          animate={{
            x: enabled ? 20 : 0,
          }}
          transition={{
            type: "spring",
            stiffness: 400,
            damping: 28,
          }}
          className="h-5 w-5 rounded-full bg-white"
        />
      </motion.button>
    </div>
  );
}