import { motion } from "framer-motion";
import type { LucideIcon } from "lucide-react";

interface Props {
  title: string;
  Icon: LucideIcon;
  active: boolean;
  onClick: () => void;
}

export default function CommandItem({
  title,
  Icon,
  active,
  onClick,
}: Props) {
  return (
    <motion.button
      whileHover={{ x: 4 }}
      onClick={onClick}
      className={`flex w-full items-center gap-3 rounded-xl px-3 py-3 transition ${
        active ? "bg-[#C7F5D9]/10" : "hover:bg-white/5"
      }`}
    >
      <Icon size={18} className="text-[#F6E7C8]" />

      <span className="text-white">{title}</span>
    </motion.button>
  );
}
