import { motion } from "framer-motion";

const radius = 54;
const circumference = 2 * Math.PI * radius;

const segments = [
  { label: "Lending", value: 68, color: "#C7F5D9" },
  { label: "Cash", value: 20, color: "#F6E7C8" },
  { label: "Reserve", value: 12, color: "#8B8B8B" },
];

export default function AllocationDonut() {
  let offset = 0;

  return (
    <div className="flex items-center justify-center">
      <svg width="180" height="180" viewBox="0 0 180 180">
        <g transform="translate(90 90)">
          <circle
            r={radius}
            fill="none"
            stroke="rgba(255,255,255,0.08)"
            strokeWidth="12"
          />

          {segments.map((s) => {
            const dash = (s.value / 100) * circumference;
            const dashOffset = offset;
            offset += dash;

            return (
              <motion.circle
                key={s.label}
                r={radius}
                fill="none"
                stroke={s.color}
                strokeWidth="12"
                strokeLinecap="round"
                pathLength={100}
                strokeDasharray={`${(dash / circumference) * 100} 100`}
                strokeDashoffset={-dashOffset / circumference * 100}
                transform="rotate(-90)"
                initial={{ pathLength: 0 }}
                animate={{ pathLength: 100 }}
                transition={{ duration: 1.2 }}
              />
            );
          })}

          <text
            y="-6"
            textAnchor="middle"
            className="fill-[#F6E7C8]"
            fontSize="20"
          >
            68%
          </text>

          <text
            y="14"
            textAnchor="middle"
            className="fill-gray-400"
            fontSize="8"
          >
            Lending
          </text>
        </g>
      </svg>
    </div>
  );
}