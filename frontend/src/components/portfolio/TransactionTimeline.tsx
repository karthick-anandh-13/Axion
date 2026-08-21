import GlassCard from "../ui/GlassCard";
import TransactionItem from "./TransactionItem";
import type { Transaction } from "../../types/transaction";

const data: Transaction[] = [
  {
    id: "1",
    title: "EMI Paid",
    amount: 18420,
    type: "DEBIT",
    category: "Repayment",
    createdAt: "Today • 09:40 AM",
  },
  {
    id: "2",
    title: "Loan Disbursed",
    amount: 850000,
    type: "CREDIT",
    category: "Home Expansion",
    createdAt: "18 Aug • 11:18 AM",
  },
  {
    id: "3",
    title: "Processing Fee",
    amount: 1250,
    type: "DEBIT",
    category: "Charges",
    createdAt: "18 Aug • 11:17 AM",
  },
];

export default function TransactionTimeline() {
  return (
    <GlassCard className="p-6">
      <div className="mb-6 flex items-center justify-between">
        <h2 className="text-2xl font-light text-white">
          Recent Activity
        </h2>

        <button className="text-sm text-[#C7F5D9] transition hover:opacity-80">
          View All
        </button>
      </div>

      <div className="space-y-5">
        {data.map((tx, i) => (
          <TransactionItem
            key={tx.id}
            transaction={tx}
            index={i}
          />
        ))}
      </div>
    </GlassCard>
  );
}