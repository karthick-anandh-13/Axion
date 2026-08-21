import { motion } from "framer-motion";

export default function AxionLogo() {
  return (
    <motion.div
      initial={{ scale: 0.6, opacity: 0 }}
      animate={{ scale: 1, opacity: 1 }}
      transition={{
        duration: 1.4,
        ease: [0.22, 1, 0.36, 1],
      }}
      className="relative flex flex-col items-center"
    >
      {/* Glow */}
      <div className="absolute h-44 w-44 rounded-full bg-[#F6E7C8]/10 blur-[80px]" />

      {/* Logo */}
      <div className="relative flex h-28 w-28 items-center justify-center rounded-32px border border-[#F6E7C8]/25 bg-white/5 backdrop-blur-xl">
        <span className="text-5xl font-extralight text-[#F6E7C8]">
          A
        </span>
      </div>

      <motion.h1
        initial={{ letterSpacing: "0.6em", opacity: 0 }}
        animate={{ letterSpacing: "0.18em", opacity: 1 }}
        transition={{ delay: 0.6, duration: 1 }}
        className="mt-7 text-3xl font-light tracking-[0.18em] text-white"
      >
        AXION
      </motion.h1>

      <motion.p
        initial={{ opacity: 0, y: 8 }}
        animate={{ opacity: 0.55, y: 0 }}
        transition={{ delay: 1 }}
        className="mt-2 text-sm tracking-[0.3em] text-white/45"
      >
        LEND • BORROW • GROW
      </motion.p>
    </motion.div>
  );
}