import { motion } from "framer-motion";

interface Props {
  progress: number;
  remaining: number;
}

export default function EmiRing({
  progress,
  remaining,
}: Props) {
  const radius = 68;
  const stroke = 10;
  const normalized = radius - stroke;
  const circumference = normalized * 2 * Math.PI;

  const offset =
    circumference - (progress / 100) * circumference;

  return (
    <div className="flex justify-center">
      <div className="relative h-40 w-40">
        <svg
          width="160"
          height="160"
          className="-rotate-90"
        >
          <circle
            cx="80"
            cy="80"
            r={normalized}
            stroke="rgba(255,255,255,0.08)"
            strokeWidth={stroke}
            fill="transparent"
          />

          <motion.circle
            cx="80"
            cy="80"
            r={normalized}
            stroke="#F6E7C8"
            strokeWidth={stroke}
            strokeLinecap="round"
            fill="transparent"
            strokeDasharray={circumference}
            initial={{
              strokeDashoffset: circumference,
            }}
            animate={{
              strokeDashoffset: offset,
            }}
            transition={{ duration: 1.6 }}
          />
        </svg>

        <div className="absolute inset-0 flex flex-col items-center justify-center">
          <p className="text-xs text-white/40">
            Remaining
          </p>

          <h2 className="text-2xl font-light text-[#F6E7C8]">
            ₹{(remaining / 100000).toFixed(1)}L
          </h2>
        </div>
      </div>
    </div>
  );
}