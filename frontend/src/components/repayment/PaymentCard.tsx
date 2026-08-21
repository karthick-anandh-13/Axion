import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";
import { CalendarDays } from "lucide-react";

interface Props {
  date: string;
  amount: number;
  status: string;
}

export default function PaymentCard({
  date,
  amount,
  status,
}: Props) {
  const due = status === "DUE";
  const paid = status === "PAID";

  return (
    <motion.div
      whileHover={{ x: 3 }}
      transition={{ duration: 0.2 }}
    >
      <GlassCard className="p-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div
              className={`rounded-xl p-2 ${
                paid
                  ? "bg-[#C7F5D9]/10"
                  : "bg-[#F6E7C8]/10"
              }`}
            >
              <CalendarDays
                size={18}
                className={
                  paid
                    ? "text-[#C7F5D9]"
                    : "text-[#F6E7C8]"
                }
              />
            </div>

            <div>
              <h3 className="text-white">{date}</h3>

              <p className="text-xs text-white/40">
                {status}
              </p>
            </div>
          </div>

          <h3
            className={`text-lg ${
              due
                ? "text-[#F6E7C8]"
                : "text-[#C7F5D9]"
            }`}
          >
            ₹{amount.toLocaleString("en-IN")}
          </h3>
        </div>
      </GlassCard>
    </motion.div>
  );
}