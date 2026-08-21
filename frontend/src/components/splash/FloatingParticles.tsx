import { motion } from "framer-motion";

const particles = Array.from({ length: 18 });

export default function FloatingParticles() {
  return (
    <div className="absolute inset-0 overflow-hidden">
      {particles.map((_, i) => (
        <motion.div
          key={i}
          initial={{
            opacity: 0,
            y: Math.random() * 800,
            x: Math.random() * 1400,
          }}
          animate={{
            opacity: [0, 0.5, 0],
            y: [null, -120],
          }}
          transition={{
            duration: 6 + Math.random() * 5,
            repeat: Infinity,
            delay: Math.random() * 5,
          }}
          className="absolute h-1.5 w-1.5 rounded-full bg-[#F6E7C8]/60 shadow-[0_0_12px_rgba(246,231,200,0.8)]"
        />
      ))}
    </div>
  );
}