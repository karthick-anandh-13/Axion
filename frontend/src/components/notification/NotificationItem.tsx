import { motion } from "framer-motion";
import { BellRing, Brain, ShieldCheck, Wallet } from "lucide-react";
import type { Notification } from "../../types/notification";

const icons = {
  PAYMENT: Wallet,
  AI: Brain,
  MARKETPLACE: BellRing,
  SECURITY: ShieldCheck,
};

interface Props {
  data: Notification;
  onRead: (id: string) => void;
}

export default function NotificationItem({ data, onRead }: Props) {
  const Icon = icons[data.category];

  return (
    <motion.button
      whileHover={{ x: 3 }}
      onClick={() => !data.read && onRead(data.id)}
      className="w-full rounded-2xl border border-white/8 bg-white/5 p-4 text-left"
    >
      <div className="flex gap-3">
        <div
          className={`rounded-xl p-2 ${
            data.read ? "bg-white/5" : "bg-[#C7F5D9]/10"
          }`}
        >
          <Icon size={18} className="text-[#F6E7C8]" />
        </div>

        <div className="flex-1">
          <div className="flex items-center justify-between">
            <h3 className="text-white">{data.title}</h3>

            {!data.read && (
              <div className="h-2 w-2 rounded-full bg-[#C7F5D9]" />
            )}
          </div>

          <p className="mt-1 text-sm text-white/45">{data.message}</p>

          <p className="mt-2 text-xs text-white/25">{data.createdAt}</p>
        </div>
      </div>
    </motion.button>
  );
}