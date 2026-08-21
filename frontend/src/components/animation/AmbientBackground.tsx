import { motion } from "framer-motion";

export default function AmbientBackground() {
  return (
    <div className="pointer-events-none fixed inset-0 overflow-hidden -z-10">
      <motion.div
        className="absolute -left-32 top-20 h-80 w-80 rounded-full bg-[rgba(199,245,217,0.08)] blur-[120px]"
        animate={{
          x: [0, 60, -40, 0],
          y: [0, -50, 30, 0],
        }}
        transition={{
          duration: 22,
          repeat: Infinity,
          ease: "easeInOut",
        }}
      />

      <motion.div
        className="absolute -right-20 bottom-10 h-72 w-72 rounded-full bg-[rgba(246,231,200,0.08)] blur-[120px]"
        animate={{
          x: [0, -70, 40, 0],
          y: [0, 40, -30, 0],
        }}
        transition={{
          duration: 26,
          repeat: Infinity,
          ease: "easeInOut",
        }}
      />
    </div>
  );
}