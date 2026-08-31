import { motion } from "framer-motion";
import { CheckCircle2, AlertCircle, Info } from "lucide-react";
import type { ToastItem } from "../../hooks/useToast";

export default function Toast({ toast }: { toast: ToastItem }) {
  const Icon =
    toast.type === "success"
      ? CheckCircle2
      : toast.type === "error"
      ? AlertCircle
      : Info;

  return (
    <motion.div
      initial={{ opacity: 0, y: -20, scale: 0.96 }}
      animate={{ opacity: 1, y: 0, scale: 1 }}
      exit={{ opacity: 0, x: 40 }}
      className="w-360px rounded-2xl border border-white/10 bg-[#111113]/90 p-4 backdrop-blur-3xl shadow-2xl"
    >
      <div className="flex gap-3">
        <Icon className="text-[#C7F5D9]" />

        <div>
          <h4 className="text-white font-medium">{toast.title}</h4>

          <p className="text-sm text-white/45">
            {toast.message}
          </p>
        </div>
      </div>
    </motion.div>
  );
}
