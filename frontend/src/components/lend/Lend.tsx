import { useMemo, useState } from "react";
import DashboardLayout from "../../layouts/DashboardLayout";
import BorrowerCard from "../../components/lend/BorrowerCard";
import { motion } from "framer-motion";

type BorrowerGrade = "A+" | "A" | "B";

type Borrower = {
  name: string;
  occupation: string;
  amount: number;
  grade: BorrowerGrade;
  roi: number;
};

const borrowers: Borrower[] = [
  {
    name: "Priya Sharma",
    occupation: "Software Engineer",
    amount: 450000,
    grade: "A+",
    roi: 11.8,
  },
  {
    name: "Rahul Iyer",
    occupation: "Business Owner",
    amount: 700000,
    grade: "A",
    roi: 13.2,
  },
  {
    name: "Meera Nair",
    occupation: "Doctor",
    amount: 300000,
    grade: "A+",
    roi: 10.9,
  },
  {
    name: "Arjun Kumar",
    occupation: "Freelancer",
    amount: 550000,
    grade: "B",
    roi: 15.4,
  },
];

export default function Lend() {
  const [filter, setFilter] = useState("all");

  const filtered = useMemo(() => {
    if (filter === "all") return borrowers;
    return borrowers.filter((b) => b.grade === filter);
  }, [filter]);

  return (
    <DashboardLayout>
      <div className="mb-8">
        <p className="text-white/40">Lend</p>
        <h1 className="text-5xl font-light text-white">
          Investment Marketplace
        </h1>
      </div>

      <div className="mb-8 flex gap-3">
        {["all", "A+", "A", "B"].map((item) => (
          <button
            key={item}
            onClick={() => setFilter(item)}
            className={`rounded-full px-5 py-2 transition ${
              filter === item
                ? "bg-[#C7F5D9]/10 text-[#C7F5D9] border border-[#C7F5D9]/40"
                : "bg-white/5 text-white/50 border border-white/10"
            }`}
          >
            {item === "all" ? "All" : item}
          </button>
        ))}
      </div>

      <div className="grid gap-5 lg:grid-cols-2">
        {filtered.map((borrower) => (
          <BorrowerCard
            key={borrower.name}
            borrower={borrower}
          />
        ))}
        <motion.div
            initial="hidden"
            animate="show"
            variants={{
                hidden: {},
                show: {
                transition: { staggerChildren: 0.12 },
                },
            }}
            className="grid gap-5 lg:grid-cols-2"
            >
            {filtered.map((borrower) => (
                <motion.div
                key={borrower.name}
                variants={{
                    hidden: { opacity: 0, y: 20 },
                    show: { opacity: 1, y: 0 },
                }}
                >
                <BorrowerCard borrower={borrower} />
                </motion.div>
            ))}
            </motion.div>
      </div>
    </DashboardLayout>
  );
}