import { AnimatePresence } from "framer-motion";
import Toast from "./Toast";
import { useToast } from "../../hooks/useToast";

export default function ToastContainer() {
  const { toasts } = useToast();

  return (
    <div className="fixed right-6 top-6 z-999 space-y-3">
      <AnimatePresence>
        {toasts.map((toast) => (
          <Toast key={toast.id} toast={toast} />
        ))}
      </AnimatePresence>
    </div>
  );
}