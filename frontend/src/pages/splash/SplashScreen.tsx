import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

import AxionLogo from "../../components/splash/AxionLogo";
import FloatingParticles from "../../components/splash/FloatingParticles";

export default function SplashScreen() {
  const navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate("/dashboard");
    }, 3000);

    return () => clearTimeout(timer);
  }, []);

  return (
    <div className="relative flex h-screen items-center justify-center overflow-hidden bg-[#070707]">
      <FloatingParticles />

      {/* Ambient glows */}
      <motion.div
        animate={{
          scale: [1, 1.2, 1],
          opacity: [0.3, 0.5, 0.3],
        }}
        transition={{
          duration: 8,
          repeat: Infinity,
        }}
        className="absolute left- -120px top-16 h-96 w-96 rounded-full bg-[#C7F5D9]/8 blur-[120px]"
      />

      <motion.div
        animate={{
          scale: [1.1, 1, 1.1],
          opacity: [0.2, 0.45, 0.2],
        }}
        transition={{
          duration: 10,
          repeat: Infinity,
        }}
        className="absolute bottom--80px right--60px h-96 w-96 rounded-full bg-[#F6E7C8]/10 blur-[140px]"
      />

      <AxionLogo />
    </div>
  );
}