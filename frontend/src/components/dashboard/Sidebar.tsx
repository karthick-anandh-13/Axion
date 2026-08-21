import { NavLink } from "react-router-dom";
import {
  LayoutDashboard,
  Wallet,
  Landmark,
  Brain,
  PieChart,
  Settings,
} from "lucide-react";

const items = [
  { icon: LayoutDashboard, label: "Dashboard", path: "/dashboard" },
  { icon: Wallet, label: "Borrow", path: "/borrow" },
  { icon: Landmark, label: "Lend", path: "/lend" },
  { icon: Brain, label: "AI", path: "/ai" },
  { icon: PieChart, label: "Portfolio", path: "/portfolio" },
  { icon: Settings, label: "Settings", path: "/settings" },
];

export default function Sidebar() {
  return (
    <aside className="w-72 p-6">
      <div className="rounded-[28px] border border-white/10 bg-white/5 backdrop-blur-3xl h-full p-5">
        <div className="mb-10">
          <h1 className="text-2xl tracking-[0.25em] text-[#F6E7C8]">
            AXION
          </h1>
          <p className="text-xs text-white/40 mt-1">
            Financial Operating System
          </p>
        </div>

        <nav className="space-y-2">
          {items.map((item) => {
            const Icon = item.icon;

            return (
              <NavLink
                key={item.label}
                to={item.path}
                className={({ isActive }) =>
                  `flex items-center gap-4 rounded-2xl px-4 py-3 transition-all ${
                    isActive
                      ? "bg-[#C7F5D9]/10 text-[#C7F5D9]"
                      : "text-white/60 hover:bg-white/5 hover:text-white"
                  }`
                }
              >
                <Icon size={20} />
                {item.label}
              </NavLink>
            );
          })}
        </nav>
      </div>
    </aside>
  );
}