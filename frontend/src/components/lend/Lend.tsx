import { useMemo, useState } from "react";
import { Search, TrendingUp, Shield, Clock } from "lucide-react";
import { useNavigate } from "react-router-dom";

type DemoLoan = {
  id: string;
  borrower: string;
  category: string;
  amount: number;
  tenure: number;
  apr: number;
  risk: "Low" | "Medium";
  funded: number;
};

const DEMO_LOANS: DemoLoan[] = [
  {
    id: "AX-001",
    borrower: "Priya Sharma",
    category: "MEDICAL",
    amount: 250000,
    tenure: 24,
    apr: 11.5,
    risk: "Low",
    funded: 82,
  },
  {
    id: "AX-002",
    borrower: "Rahul Kumar",
    category: "BUSINESS",
    amount: 850000,
    tenure: 36,
    apr: 13.8,
    risk: "Medium",
    funded: 61,
  },
  {
    id: "AX-003",
    borrower: "Ananya Reddy",
    category: "EDUCATION",
    amount: 420000,
    tenure: 24,
    apr: 10.9,
    risk: "Low",
    funded: 95,
  },
  {
    id: "AX-004",
    borrower: "Vikram Patel",
    category: "HOME_REPAIR",
    amount: 180000,
    tenure: 12,
    apr: 11.2,
    risk: "Low",
    funded: 48,
  },
  {
    id: "AX-005",
    borrower: "Kavin Mohan",
    category: "VEHICLE",
    amount: 600000,
    tenure: 36,
    apr: 12.7,
    risk: "Medium",
    funded: 73,
  },
  {
    id: "AX-006",
    borrower: "Meera Devi",
    category: "AGRICULTURE",
    amount: 320000,
    tenure: 24,
    apr: 11.8,
    risk: "Low",
    funded: 67,
  },
];

export default function Lend() {
  const navigate = useNavigate();
  const [search, setSearch] = useState("");
  const [filter, setFilter] = useState("All");

  const loans = useMemo(() => {
    return DEMO_LOANS.filter((loan) => {
      const matchesSearch =
        loan.borrower.toLowerCase().includes(search.toLowerCase()) ||
        loan.category.toLowerCase().includes(search.toLowerCase());

      const matchesFilter =
        filter === "All" ||
        (filter === "Low Risk" && loan.risk === "Low") ||
        (filter === "24M" && loan.tenure === 24) ||
        (filter === "36M" && loan.tenure === 36);

      return matchesSearch && matchesFilter;
    });
  }, [search, filter]);

  return (
    <div className="space-y-6">
      <div>
        <p className="text-sm text-white/40">Lending Marketplace</p>
        <h1 className="mt-2 text-5xl font-light text-white">
          Invest Intelligently
        </h1>
      </div>

      <div className="relative">
        <Search
          size={20}
          className="absolute left-4 top-1/2 -translate-y-1/2 text-white/30"
        />
        <input
          type="text"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          placeholder="Search opportunities..."
          className="w-full rounded-2xl border border-white/10 bg-white/5 py-4 pl-12 pr-4 text-white placeholder:text-white/30 outline-none backdrop-blur-xl"
        />
      </div>

      <div className="flex flex-wrap gap-3">
        {["All", "Low Risk", "24M", "36M"].map((item) => (
          <button
            key={item}
            onClick={() => setFilter(item)}
            className={`rounded-full px-5 py-2 transition ${
              filter === item
                ? "border border-[#C7F5D9]/40 bg-[#C7F5D9]/10 text-[#C7F5D9]"
                : "border border-white/10 bg-white/5 text-white/50"
            }`}
          >
            {item}
          </button>
        ))}
      </div>

      <div className="grid gap-5 lg:grid-cols-2">
        {loans.map((loan) => (
          <div
            key={loan.id}
            className="rounded-3xl border border-white/10 bg-white/5 p-6 backdrop-blur-xl"
          >
            <div className="mb-4 flex items-center justify-between">
              <div>
                <h3 className="text-xl text-white">{loan.borrower}</h3>
                <p className="text-sm text-white/40">
                  {loan.category.replaceAll("_", " ")}
                </p>
              </div>

              <span
                className={`rounded-full px-3 py-1 text-xs ${
                  loan.risk === "Low"
                    ? "bg-green-500/10 text-green-300"
                    : "bg-yellow-500/10 text-yellow-300"
                }`}
              >
                {loan.risk} Risk
              </span>
            </div>

            <div className="mb-5">
              <h2 className="text-3xl font-light text-[#F6E7C8]">
                ₹{new Intl.NumberFormat("en-IN").format(loan.amount)}
              </h2>
            </div>

            <div className="mb-5 grid grid-cols-3 gap-4 text-sm">
              <div>
                <div className="mb-1 flex items-center gap-1 text-white/40">
                  <TrendingUp size={14} />
                  APR
                </div>
                <p className="text-white">{loan.apr}%</p>
              </div>

              <div>
                <div className="mb-1 flex items-center gap-1 text-white/40">
                  <Clock size={14} />
                  Tenure
                </div>
                <p className="text-white">{loan.tenure}M</p>
              </div>

              <div>
                <div className="mb-1 flex items-center gap-1 text-white/40">
                  <Shield size={14} />
                  AI
                </div>
                <p className="text-[#C7F5D9]">Verified</p>
              </div>
            </div>

            <div className="mb-2 flex justify-between text-sm">
              <span className="text-white/50">Funding Progress</span>
              <span className="text-white">{loan.funded}%</span>
            </div>

            <div className="mb-5 h-2 rounded-full bg-white/10">
              <div
                className="h-full rounded-full bg-gradient-to-r from-[#C7F5D9] to-[#F6E7C8]"
                style={{ width: `${loan.funded}%` }}
              />
            </div>

            <button
              onClick={() => navigate(`/marketplace/${loan.id}`)}
              className="w-full rounded-xl border border-[#C7F5D9]/20 bg-[#C7F5D9]/10 py-3 text-[#C7F5D9] transition hover:bg-[#C7F5D9]/20"
            >
              Invest Now
            </button>
          </div>
        ))}
      </div>

      {loans.length === 0 && (
        <div className="rounded-3xl border border-white/10 bg-white/5 p-10 text-center text-white/50">
          No lending opportunities found.
        </div>
      )}
    </div>
  );
}