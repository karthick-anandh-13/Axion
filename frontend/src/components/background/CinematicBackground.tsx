import { motion } from "framer-motion";

export default function CinematicBackground() {
  return (
    <div className="fixed inset-0 -z-10 overflow-hidden bg-[#111111]">
      {/* Emerald glow */}
      <motion.div
        animate={{
          x: [0, 120, -80, 0],
          y: [0, -60, 80, 0],
          scale: [1, 1.2, 0.9, 1],
        }}
        transition={{
          duration: 22,
          repeat: Infinity,
          ease: "easeInOut",
        }}
        className="absolute left-[-10%] top-[-10%] h-[500px] w-[500px] rounded-full bg-[#2F8F6B]/30 blur-[120px]"
      />

      {/* Champagne glow */}
      <motion.div
        animate={{
          x: [0, -100, 60, 0],
          y: [0, 100, -40, 0],
          scale: [1, 1.15, 1, 1],
        }}
        transition={{
          duration: 26,
          repeat: Infinity,
          ease: "easeInOut",
        }}
        className="absolute bottom-[-15%] right-[-10%] h-[420px] w-[420px] rounded-full bg-[#F6E7C8]/20 blur-[130px]"
      />

      {/* Ambient mist */}
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_center,rgba(255,255,255,0.03),transparent_65%)]" />

      {/* Film grain */}
      <div className="absolute inset-0 opacity-[0.03] [background-image:radial-gradient(white_1px,transparent_1px)] [background-size:18px_18px]" />
      
        <motion.div
        animate={{
            y: [-20, 20, -20],
            x: [-10, 30, -10],
        }}
        transition={{
            duration: 14,
            repeat: Infinity,
            ease: "easeInOut",
        }}
        className="absolute right-[18%] top-[18%] h-40 w-40 rounded-full border border-white/10 bg-white/5 backdrop-blur-3xl"
        />

        <motion.div
        animate={{
            y: [20, -30, 20],
            x: [0, -25, 0],
        }}
        transition={{
            duration: 18,
            repeat: Infinity,
            ease: "easeInOut",
        }}
        className="absolute bottom-[12%] left-[15%] h-24 w-24 rounded-full bg-[#C7F5D9]/10 blur-xl"
        />
    </div>
  );
}