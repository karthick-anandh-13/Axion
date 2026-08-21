import { motion } from "framer-motion";

interface Props {
  score: number;
}

export default function CreditGauge({ score }: Props) {
  const radius = 70;
  const stroke = 12;

  const normalized = radius - stroke;

  const circumference = normalized * 2 * Math.PI;

  const progress = (score / 900) * circumference;

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
            stroke="#C7F5D9"
            strokeWidth={stroke}
            strokeLinecap="round"
            fill="transparent"
            strokeDasharray={circumference}
            initial={{
              strokeDashoffset: circumference,
            }}
            animate={{
              strokeDashoffset:
                circumference - progress,
            }}
            transition={{ duration: 1.8 }}
          />
        </svg>

        <div className="absolute inset-0 flex flex-col items-center justify-center">
          <p className="text-xs text-white/40">
            Credit
          </p>

          <h2 className="text-4xl font-light text-[#C7F5D9]">
            {score}
          </h2>
        </div>
      </div>
    </div>
  );
}