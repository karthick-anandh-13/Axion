import { motion, type Variants } from "framer-motion";
import type { ReactNode } from "react";

interface Props {
  children: ReactNode;
}

const variants: Variants = {
  initial: {
    opacity: 0,
    y: 20,
    filter: "blur(12px)",
  },
  animate: {
    opacity: 1,
    y: 0,
    filter: "blur(0px)",
    transition: {
      duration: 0.55,
      ease: [0.22, 1, 0.36, 1],
    },
  },
  exit: {
    opacity: 0,
    y: -12,
    filter: "blur(8px)",
    transition: {
      duration: 0.28,
      ease: "easeInOut",
    },
  },
};

export default function PageTransition({ children }: Props) {
  return (
    <motion.main
      className="min-h-screen"
      variants={variants}
      initial="initial"
      animate="animate"
      exit="exit"
    >
      {children}
    </motion.main>
  );
}