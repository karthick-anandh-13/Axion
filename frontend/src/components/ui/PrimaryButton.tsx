import { motion } from "framer-motion";
import type { ReactNode } from "react";

interface Props {
  children: ReactNode;
  disabled?: boolean;
  onClick?: () => void;
}

export default function PrimaryButton({
  children,
  disabled = false,
  onClick,
}: Props) {
  return (
    <motion.button
      whileHover={!disabled ? { scale: 1.02 } : {}}
      whileTap={!disabled ? { scale: 0.98 } : {}}
      onClick={onClick}
      disabled={disabled}
      className={`
        w-full rounded-2xl py-4 font-semibold transition-all

        ${
          disabled
            ? "bg-white/10 text-white/30 cursor-not-allowed"
            : "bg-[#F6E7C8] text-black hover:shadow-[0_0_40px_rgba(246,231,200,0.25)]"
        }
      `}
    >
      {children}
    </motion.button>
  );
}