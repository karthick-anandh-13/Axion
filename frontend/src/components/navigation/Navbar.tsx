import { motion } from "framer-motion";
import { Link, useLocation } from "react-router-dom";
import { Landmark, Bell, User } from "lucide-react";

export default function Navbar() {
  const location = useLocation();

  const menuItems = [
    { name: "Borrow", path: "/borrow" },
    { name: "Lend", path: "/marketplace" },
    { name: "AI", path: "/ai" },
    { name: "Portfolio", path: "/portfolio" },
  ];

  return (
    <motion.header
      initial={{ y: -40, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      transition={{ duration: 0.6 }}
      className="fixed top-6 left-1/2 z-50 w-[92%] max-w-7xl -translate-x-1/2"
    >
      <nav className="flex items-center justify-between rounded-full border border-white/10 bg-white/8 px-6 py-3 backdrop-blur-3xl shadow-[0_12px_50px_rgba(0,0,0,0.35)]">
        {/* Logo */}
        <Link to="/home" className="flex items-center gap-3">
          <div className="flex h-11 w-11 items-center justify-center rounded-full border border-[#F6E7C8]/20 bg-[#F6E7C8]/10">
            <Landmark className="h-5 w-5 text-[#F6E7C8]" />
          </div>

          <div>
            <h1 className="text-lg font-semibold tracking-[0.18em] text-[#F6E7C8]">
              AXION
            </h1>
            <p className="text-[10px] uppercase tracking-[0.25em] text-white/40">
              Private Capital
            </p>
          </div>
        </Link>

        {/* Center Menu */}
        <div className="hidden items-center gap-8 text-sm md:flex">
          {menuItems.map((item) => {
            const active = location.pathname === item.path;

            return (
              <Link key={item.name} to={item.path}>
                <motion.div
                  whileHover={{ y: -2 }}
                  className={`relative transition ${
                    active
                      ? "text-[#F6E7C8]"
                      : "text-white/70 hover:text-[#F6E7C8]"
                  }`}
                >
                  {item.name}

                  {active && (
                    <motion.div
                      layoutId="navbar-indicator"
                      className="absolute -bottom-2 left-0 h-0.5 w-full rounded-full bg-[#F6E7C8]"
                    />
                  )}
                </motion.div>
              </Link>
            );
          })}
        </div>

        {/* Right Icons */}
        <div className="flex items-center gap-3">
          <button className="rounded-full p-2 text-white/60 transition hover:bg-white/10 hover:text-[#F6E7C8]">
            <Bell size={18} />
          </button>

          <Link
            to="/settings"
            className="rounded-full border border-white/10 bg-white/5 p-2 transition hover:border-[#F6E7C8]/20"
          >
            <User size={18} className="text-[#F6E7C8]" />
          </Link>
        </div>
      </nav>
    </motion.header>
  );
}