import { motion } from "framer-motion";
import React from "react";

const filters = ["All", "Low Risk", "24M", "36M"] as const;

type Filter = (typeof filters)[number];

interface FilterChipsProps {
  selected: Filter;
  onSelect: (value: Filter) => void;
}

export default function FilterChips({
  selected,
  onSelect,
}: FilterChipsProps) {
  return React.createElement(
    "div",
    { className: "flex gap-3 overflow-x-auto pb-1" },
    ...filters.map((filter) =>
      React.createElement(
        motion.button,
        {
          key: filter,
          whileTap: { scale: 0.95 },
          onClick: () => onSelect(filter),
          className:
            selected === filter
              ? "rounded-full border border-[#C7F5D9]/30 bg-[#C7F5D9]/10 px-4 py-2 text-sm text-[#C7F5D9] transition"
              : "rounded-full border border-white/10 bg-white/5 px-4 py-2 text-sm text-white/45 transition",
        },
        filter
      )
    )
  );
}