import { Bell } from "lucide-react";
import { motion } from "framer-motion";
import { useState } from "react";
import NotificationDrawer from "./NotificationDrawer";
import { useNotifications } from "../../hooks/useNotifications";

export default function NotificationBell() {
  const [open, setOpen] = useState(false);

  const { data } = useNotifications();

  const unread = data?.filter((n) => !n.read).length ?? 0;

  return (
    <>
      <motion.button
        whileTap={{ scale: 0.9 }}
        onClick={() => setOpen(true)}
        className="relative rounded-full border border-white/10 bg-white/5 p-3"
      >
        <Bell size={20} className="text-white" />

        {unread > 0 && (
          <div className="absolute -right-1 -top-1 flex h-5 w-5 items-center justify-center rounded-full bg-[#C7F5D9] text-[10px] font-bold text-black">
            {unread}
          </div>
        )}
      </motion.button>

      <NotificationDrawer open={open} onClose={() => setOpen(false)} />
    </>
  );
}