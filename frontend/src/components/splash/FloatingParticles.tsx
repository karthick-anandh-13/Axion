import { motion } from "framer-motion";

const particles = Array.from({ length: 18 }, (_, index) => ({
  delay: (index * 1.7) % 5,
  duration: 6 + (index % 5),
  x: (index * 149) % 1400,
  y: (index * 83) % 800,
}));

export default function FloatingParticles() {
  return (
    <div className="absolute inset-0 overflow-hidden">
      {particles.map((particle, i) => (
        <motion.div
          key={i}
          initial={{
            opacity: 0,
            y: particle.y,
            x: particle.x,
          }}
          animate={{
            opacity: [0, 0.5, 0],
            y: [null, -120],
          }}
          transition={{
            duration: particle.duration,
            repeat: Infinity,
            delay: particle.delay,
          }}
          className="absolute h-1.5 w-1.5 rounded-full bg-[#F6E7C8]/60 shadow-[0_0_12px_rgba(246,231,200,0.8)]"
        />
      ))}
    </div>
  );
}
