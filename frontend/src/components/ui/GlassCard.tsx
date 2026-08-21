import { motion } from "framer-motion";
import type { ReactNode } from "react";
import { cn } from "../../lib/utils";

interface GlassCardProps {
  children: ReactNode;
  className?: string;
  hover?: boolean;
}

const smoothEase = [0.22, 1, 0.36, 1] as const;

export default function GlassCard({
  children,
  className,
  hover = true,
}: GlassCardProps) {
  return (
    <motion.div
      initial={{ opacity: 0, y: 16 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{
        duration: 0.45,
        ease: smoothEase,
      }}
      whileHover={
        hover
          ? {
              y: -6,
              scale: 1.01,
              rotateX: 2,
              rotateY: -2,
              transition: {
                type: "spring",
                stiffness: 260,
                damping: 18,
              },
            }
          : undefined
      }
      style={{
        transformStyle: "preserve-3d",
        perspective: 1000,
      }}
      className={cn(
        "group relative overflow-hidden rounded-[30px]",
        "border border-white/0.08",
        "bg-white/0.045",
        "backdrop-blur-32px",
        "shadow-[0_30px_80px_rgba(0,0,0,0.45)]",
        className
      )}
    >
      {/* Emerald ambient glow */}
      <motion.div
        animate={{
          opacity: [0.55, 0.75, 0.55],
          scale: [1, 1.05, 1],
        }}
        transition={{
          duration: 6,
          repeat: Infinity,
          ease: "easeInOut",
        }}
        className="absolute -left-24 -top-24 h-56 w-56 rounded-full bg-[#7FE3B0]/10 blur-3xl"
      />

      {/* Champagne glow */}
      <motion.div
        animate={{
          opacity: [0.4, 0.65, 0.4],
          scale: [1, 1.08, 1],
        }}
        transition={{
          duration: 8,
          repeat: Infinity,
          ease: "easeInOut",
        }}
        className="absolute -bottom-20 -right-20 h-48 w-48 rounded-full bg-[#F6E7C8]/10 blur-3xl"
      />

      {/* Glass reflection */}
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_top,rgba(255,255,255,0.10),transparent_55%)] opacity-70" />

      {/* Top highlight */}
      <div className="absolute inset-x-6 top-0 h-px bg-linear-to-r from-transparent via-[#F6E7C8]/70 to-transparent" />

      {/* Side light */}
      <div className="absolute left-0 top-8 h-32 w-px bg-linear-to-b from-transparent via-white/20 to-transparent" />

      {/* Hover shimmer */}
      <div className="absolute inset-0 -translate-x-full bg-linear-to-r from-transparent via-white/0.06 to-transparent transition-transform duration-1000 ease-out group-hover:translate-x-full" />

      {/* Inner border */}
      <div className="pointer-events-none absolute inset-1px rounded-[29px] border border-white/0.04" />

      {/* Content */}
      <div className="relative z-10">{children}</div>
    </motion.div>
  );
}