import { Bell, Search } from "lucide-react";

export default function Topbar() {
  return (
    <header className="mb-8 flex items-center justify-between">
      <div className="relative w-[380px]">
        <Search
          className="absolute left-4 top-1/2 -translate-y-1/2 text-white/30"
          size={18}
        />

        <input
          placeholder="Search loans, users, portfolio..."
          className="w-full rounded-2xl border border-white/10 bg-white/5 py-3 pl-11 pr-4 text-white outline-none backdrop-blur-xl placeholder:text-white/30"
        />
      </div>

      <div className="flex items-center gap-4">
        <button className="rounded-2xl border border-white/10 bg-white/5 p-3">
          <Bell className="text-white/70" size={20} />
        </button>

        <div className="flex items-center gap-3 rounded-2xl border border-white/10 bg-white/5 px-3 py-2">
          <div className="h-10 w-10 rounded-full bg-[#F6E7C8]/10" />
          <div>
            <p className="text-sm text-white">Karthick</p>
            <p className="text-xs text-white/40">Verified</p>
          </div>
        </div>
      </div>
    </header>
  );
}