import { AnimatePresence, motion } from "framer-motion";
import NotificationItem from "./NotificationItem";
import { useNotifications } from "../../hooks/useNotifications";

interface Props {
  open: boolean;
  onClose: () => void;
}

export default function NotificationDrawer({ open, onClose }: Props) {
  const { data, readMutation } = useNotifications();

  return (
    <AnimatePresence>
      {open && (
        <>
          <motion.div
            onClick={onClose}
            className="fixed inset-0 z-40 bg-black/40 backdrop-blur-sm"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
          />

          <motion.div
            initial={{ x: 420 }}
            animate={{ x: 0 }}
            exit={{ x: 420 }}
            transition={{ type: "spring", damping: 24 }}
            className="fixed right-0 top-0 z-50 h-screen w-420px border-l border-white/10 bg-[#0B0B0D]/90 p-6 backdrop-blur-3xl"
          >
            <h2 className="mb-6 text-3xl font-light text-white">Notifications</h2>

            <div className="space-y-3">
              {data?.map((n) => (
                <NotificationItem
                  key={n.id}
                  data={n}
                  onRead={(id) => readMutation.mutate(id)}
                />
              ))}
            </div>
          </motion.div>
        </>
      )}
    </AnimatePresence>
  );
}