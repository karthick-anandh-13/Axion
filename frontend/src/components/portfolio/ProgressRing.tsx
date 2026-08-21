import { motion } from "framer-motion";

interface Props {
  progress: number;
}

export default function ProgressRing({ progress }: Props) {
  const radius = 28;
  const stroke = 5;
  const normalized = radius - stroke;
  const circumference = normalized * 2 * Math.PI;

  const offset =
    circumference - (progress / 100) * circumference;

  return (
    <div className="relative h-16 w-16">
      <svg
        height="64"
        width="64"
        className="-rotate-90"
      >
        <circle
          stroke="rgba(255,255,255,0.12)"
          fill="transparent"
          strokeWidth={stroke}
          r={normalized}
          cx="32"
          cy="32"
        />

        <motion.circle
          stroke="#C7F5D9"
          fill="transparent"
          strokeWidth={stroke}
          strokeLinecap="round"
          r={normalized}
          cx="32"
          cy="32"
          strokeDasharray={`${circumference} ${circumference}`}
          initial={{ strokeDashoffset: circumference }}
          animate={{ strokeDashoffset: offset }}
          transition={{ duration: 1.4 }}
        />
      </svg>

      <div className="absolute inset-0 flex items-center justify-center">
        <span className="text-xs text-white">
          {progress}%
        </span>
      </div>
    </div>
  );
}