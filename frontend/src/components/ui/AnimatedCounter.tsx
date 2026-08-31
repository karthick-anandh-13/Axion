import { motion, useMotionValue, useSpring } from "framer-motion";
import { useEffect } from "react";

interface Props {
  value: number;
  prefix?: string;
}

export default function AnimatedCounter({
  value,
  prefix = "",
}: Props) {
  const motionValue = useMotionValue(0);

  const spring = useSpring(motionValue, {
    stiffness: 70,
    damping: 20,
  });

  useEffect(() => {
    motionValue.set(value);
  }, [motionValue, value]);

  return (
    <motion.span>
      {prefix}
      {spring.get().toLocaleString("en-IN")}
    </motion.span>
  );
}
