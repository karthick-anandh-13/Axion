import { motion } from "framer-motion";
import { ArrowDownLeft, ArrowUpRight } from "lucide-react";
import type { Transaction } from "../../types/transaction";

interface Props {
  transaction: Transaction;
  index: number;
}

export default function TransactionItem({
  transaction,
  index,
}: Props) {
  const credit = transaction.type === "CREDIT";

  return (
    <motion.div
      initial={{ opacity: 0, x: -20 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ delay: index * 0.08 }}
      className="relative flex gap-4"
    >
      <div className="flex flex-col items-center">
        <div
          className={`flex h-11 w-11 items-center justify-center rounded-full ${
            credit
              ? "bg-[#C7F5D9]/12"
              : "bg-[#F6E7C8]/10"
          }`}
        >
          {credit ? (
            <ArrowDownLeft
              size={18}
              className="text-[#C7F5D9]"
            />
          ) : (
            <ArrowUpRight
              size={18}
              className="text-[#F6E7C8]"
            />
          )}
        </div>

        {index !== 2 && (
          <div className="mt-2 h-full w-px bg-white/10" />
        )}
      </div>

      <div className="flex-1 rounded-2xl border border-white/8 bg-white/4 p-4">
        <div className="flex items-start justify-between">
          <div>
            <h3 className="text-white">
              {transaction.title}
            </h3>

            <p className="mt-1 text-xs text-white/40">
              {transaction.category}
            </p>
          </div>

          <h4
            className={`text-lg font-light ${
              credit
                ? "text-[#C7F5D9]"
                : "text-[#F6E7C8]"
            }`}
          >
            {credit ? "+" : "-"}₹
            {Math.abs(transaction.amount).toLocaleString(
              "en-IN"
            )}
          </h4>
        </div>

        <p className="mt-3 text-xs text-white/35">
          {transaction.createdAt}
        </p>
      </div>
    </motion.div>
  );
}