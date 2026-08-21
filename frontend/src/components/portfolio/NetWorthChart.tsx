import { motion } from "framer-motion";
import GlassCard from "../ui/GlassCard";

const values = [3.2, 4.5, 4.1, 6.8, 6.2, 8.7, 10.1];
const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"];

export default function NetWorthChart() {
  const width = 640;
  const height = 220;
  const padding = 28;

  const max = Math.max(...values);
  const min = Math.min(...values);

  const points = values.map((v, i) => {
    const x =
      padding +
      (i * (width - padding * 2)) / (values.length - 1);

    const y =
      height -
      padding -
      ((v - min) / (max - min)) *
        (height - padding * 2);

    return { x, y };
  });

  const line = points
    .map((p, i) =>
      `${i === 0 ? "M" : "L"} ${p.x} ${p.y}`
    )
    .join(" ");

  const area =
    line +
    ` L ${width - padding} ${height - padding}` +
    ` L ${padding} ${height - padding} Z`;

  return (
    <GlassCard className="p-6">
      <div className="mb-5">
        <p className="text-sm text-white/40">
          Portfolio Trend
        </p>

        <h3 className="text-2xl font-light text-[#F6E7C8]">
          Net Worth Growth
        </h3>
      </div>

      <svg
        viewBox={`0 0 ${width} ${height}`}
        className="w-full"
      >
        <defs>
          <linearGradient
            id="lineGradient"
            x1="0%"
            y1="0%"
            x2="100%"
            y2="0%"
          >
            <stop offset="0%" stopColor="#C7F5D9" />
            <stop offset="100%" stopColor="#F6E7C8" />
          </linearGradient>

          <linearGradient
            id="fillGradient"
            x1="0"
            y1="0"
            x2="0"
            y2="1"
          >
            <stop
              offset="0%"
              stopColor="#C7F5D9"
              stopOpacity="0.18"
            />
            <stop
              offset="100%"
              stopColor="#C7F5D9"
              stopOpacity="0"
            />
          </linearGradient>
        </defs>

        {[0, 1, 2, 3, 4].map((i) => (
          <line
            key={i}
            x1={padding}
            y1={28 + i * 40}
            x2={width - padding}
            y2={28 + i * 40}
            stroke="rgba(255,255,255,0.06)"
            strokeDasharray="5 6"
          />
        ))}

        <motion.path
          d={area}
          fill="url(#fillGradient)"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 1 }}
        />

        <motion.path
          d={line}
          fill="none"
          stroke="url(#lineGradient)"
          strokeWidth={4}
          strokeLinecap="round"
          initial={{ pathLength: 0 }}
          animate={{ pathLength: 1 }}
          transition={{
            duration: 2,
            ease: "easeOut",
          }}
        />

        {points.map((p, i) => (
          <motion.circle
            key={i}
            cx={p.x}
            cy={p.y}
            r={5}
            fill="#C7F5D9"
            initial={{ scale: 0 }}
            animate={{ scale: 1 }}
            transition={{
              delay: i * 0.15,
              type: "spring",
            }}
          />
        ))}

        {months.map((m, i) => (
          <text
            key={m}
            x={points[i].x}
            y={height - 6}
            textAnchor="middle"
            fill="#737373"
            fontSize="11"
          >
            {m}
          </text>
        ))}
      </svg>
    </GlassCard>
  );
}