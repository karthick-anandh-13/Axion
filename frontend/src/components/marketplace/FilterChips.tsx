import { motion } from "framer-motion";

const filters = ["All", "Low Risk", "24M", "36M"];

interface Props {
  selected: string;
  onSelect: (v: string) => void;
}

export default function FilterChips({
  selected,
  onSelect,
}: Props) {
  return (
    <div className="flex gap-3 overflow-x-auto pb-1">
      {filters.map((f) => (
        <motion.button
          whileTap={{ scale: 0.95 }}
          key={f}
          onClick={() => onSelect(f)}
          className={`rounded-full px-4 py-2 text-sm transition ${
            selected === f
              ? "bg-[#C7F5D9]/10 text-[#C7F5D9] border border-[#C7F5D9]/30"
              : "bg-white/5 text-white/45 border border-white/10"
          }`}
        >
          {f}
        </motion.button>
      ))}
    </div>
  );
}