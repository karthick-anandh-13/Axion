import { motion } from "framer-motion";
import { Landmark, Bell, User } from "lucide-react";

export default function Navbar() {
  return (
    <motion.header
      initial={{ y: -40, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      transition={{ duration: 0.6 }}
      className="fixed top-6 left-1/2 z-50 w-[92%] max-w-7xl -translate-x-1/2"
    >
      <nav className="flex items-center justify-between rounded-full border border-white/10 bg-white/8 px-6 py-3 backdrop-blur-3xl shadow-[0_12px_50px_rgba(0,0,0,0.35)]">

        {/* Logo */}
        <div className="flex items-center gap-3">
          <div className="flex h-11 w-11 items-center justify-center rounded-full bg-[#F6E7C8]/10 border border-[#F6E7C8]/20">
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
        </div>

        {/* Center Menu */}
        <div className="hidden md:flex items-center gap-8 text-sm">
          {["Borrow", "Lend", "AI", "Portfolio"].map((item) => (
            <motion.button
              key={item}
              whileHover={{ y: -2 }}
              className="relative text-white/70 transition hover:text-[#F6E7C8]"
            >
              {item}
            </motion.button>
          ))}
        </div>

        {/* Right Icons */}
        <div className="flex items-center gap-3">
          <button className="rounded-full p-2 text-white/60 hover:bg-white/10 hover:text-[#F6E7C8]">
            <Bell size={18} />
          </button>

          <button className="rounded-full border border-white/10 bg-white/5 p-2 hover:border-[#F6E7C8]/20">
            <User size={18} className="text-[#F6E7C8]" />
          </button>
        </div>
      </nav>
    </motion.header>
  );
}