import { AnimatePresence, motion } from "framer-motion";
import { Loader2 } from "lucide-react";
import { useLoading } from "../../hooks/useLoading";

export default function LoadingOverlay() {
  const { loading } = useLoading();

  return (
    <AnimatePresence>
      {loading && (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          className="fixed inset-0 z-999 flex items-center justify-center bg-black/60 backdrop-blur-xl"
        >
          <motion.div
            initial={{ scale: 0.9 }}
            animate={{ scale: 1 }}
            className="rounded-3xl border border-white/10 bg-white/5 p-8 backdrop-blur-3xl"
          >
            <Loader2 className="mx-auto mb-4 h-12 w-12 animate-spin text-[#F6E7C8]" />

            <h3 className="text-center text-lg text-white">
              AXION
            </h3>

            <p className="mt-1 text-center text-sm text-white/40">
              Processing securely...
            </p>
          </motion.div>
        </motion.div>
      )}
    </AnimatePresence>
  );
}