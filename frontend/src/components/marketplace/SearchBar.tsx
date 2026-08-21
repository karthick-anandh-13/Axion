import { Search } from "lucide-react";
import { motion } from "framer-motion";

interface Props {
  value: string;
  onChange: (v: string) => void;
}

export default function SearchBar({
  value,
  onChange,
}: Props) {
  return (
    <motion.div
      initial={{ opacity: 0, y: -12 }}
      animate={{ opacity: 1, y: 0 }}
      className="relative"
    >
      <Search
        size={18}
        className="absolute left-4 top-1/2 -translate-y-1/2 text-white/30"
      />

      <input
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder="Search opportunities..."
        className="w-full rounded-2xl border border-white/10 bg-white/5 py-3 pl-11 pr-4 text-white placeholder:text-white/25 outline-none transition focus:border-[#C7F5D9]/30"
      />
    </motion.div>
  );
}