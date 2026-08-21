import { AnimatePresence, motion } from "framer-motion";
import { Search } from "lucide-react";
import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";

import CommandItem from "./CommandItem";
import { commands } from "../../hooks/useCommand";

export default function CommandPalette() {
  const navigate = useNavigate();

  const [open, setOpen] = useState(false);
  const [query, setQuery] = useState("");

  useEffect(() => {
    const handler = (e: globalThis.KeyboardEvent) => {
      if ((e.ctrlKey || e.metaKey) && e.key.toLowerCase() === "k") {
        e.preventDefault();
        setOpen((prev) => !prev);
      }

      if (e.key === "Escape") {
        setOpen(false);
        setQuery("");
      }
    };

    window.addEventListener("keydown", handler);
    return () => window.removeEventListener("keydown", handler);
  }, []);

  const filtered = useMemo(() => {
    return commands.filter((cmd) =>
      cmd.title.toLowerCase().includes(query.toLowerCase())
    );
  }, [query]);

  const closePalette = () => {
    setOpen(false);
    setQuery("");
  };

  return (
    <AnimatePresence>
      {open && (
        <>
          <motion.div
            className="fixed inset-0 z-100 bg-black/50 backdrop-blur-md"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            onClick={closePalette}
          />

          <motion.div
            className="fixed left-1/2 top-24 z-101 w-full max-w-560px -translate-x-1/2 rounded-[28px] border border-white/10 bg-[#0B0B0D]/90 p-4 shadow-2xl backdrop-blur-3xl"
            initial={{ opacity: 0, y: -40, scale: 0.96 }}
            animate={{ opacity: 1, y: 0, scale: 1 }}
            exit={{ opacity: 0, y: -20 }}
            transition={{ type: "spring", damping: 24 }}
          >
            <div className="mb-4 flex items-center gap-3 rounded-xl bg-white/5 px-4 py-3">
              <Search size={18} className="text-white/30" />

              <input
                autoFocus
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                placeholder="Search pages..."
                className="w-full bg-transparent text-white outline-none placeholder:text-white/25"
              />
            </div>

            <div className="space-y-1">
              {filtered.length > 0 ? (
                filtered.map((item, index) => (
                  <CommandItem
                    key={item.path}
                    title={item.title}
                    Icon={item.icon}
                    active={index === 0}
                    onClick={() => {
                      navigate(item.path);
                      closePalette();
                    }}
                  />
                ))
              ) : (
                <p className="py-6 text-center text-sm text-white/40">
                  No matching pages found
                </p>
              )}
            </div>

            <div className="mt-4 border-t border-white/5 pt-3 text-center text-xs text-white/25">
              ESC to close • Ctrl + K to open
            </div>
          </motion.div>
        </>
      )}
    </AnimatePresence>
  );
}