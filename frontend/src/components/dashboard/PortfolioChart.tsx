import { motion } from "framer-motion";
import { useState } from "react";

const data = [
  { month: "Jan", value: "₹2.8M", x: 20, y: 150 },
  { month: "Feb", value: "₹3.4M", x: 80, y: 135 },
  { month: "Mar", value: "₹4.1M", x: 140, y: 115 },
  { month: "Apr", value: "₹5.3M", x: 200, y: 95 },
  { month: "May", value: "₹7.8M", x: 260, y: 70 },
  { month: "Jun", value: "₹12.4M", x: 320, y: 40 },
];

const chartPath =
  "M20 150 C50 145,65 138,80 135 S125 120,140 115 S185 105,200 95 S245 80,260 70 S300 50,320 40";

export default function PortfolioChart() {
  const [active, setActive] = useState(5);

  return (
    <div className="w-full">
      <div className="mb-6 flex items-center justify-between">
        <div>
          <p className="text-sm text-white/40">
            Portfolio Performance
          </p>

          <h2 className="mt-1 text-3xl font-light text-[#F6E7C8]">
            {data[active].value}
          </h2>
        </div>

        <div className="rounded-full bg-[#C7F5D9]/10 px-4 py-2 text-sm text-[#C7F5D9]">
          +18.4%
        </div>
      </div>

      <svg viewBox="0 0 340 180" className="w-full overflow-visible">
        <defs>
          <linearGradient id="portfolioLine" x1="0" y1="0" x2="1" y2="0">
            <stop offset="0%" stopColor="#C7F5D9" />
            <stop offset="100%" stopColor="#F6E7C8" />
          </linearGradient>
        </defs>

        {[40, 70, 100, 130].map((y) => (
          <line
            key={y}
            x1="20"
            y1={y}
            x2="320"
            y2={y}
            stroke="rgba(255,255,255,0.05)"
          />
        ))}

        <motion.path
          d={chartPath}
          fill="none"
          stroke="url(#portfolioLine)"
          strokeWidth="3.5"
          strokeLinecap="round"
          initial={{ pathLength: 0 }}
          animate={{ pathLength: 1 }}
          transition={{ duration: 1.8 }}
        />

        {data.map((point, index) => (
          <g key={point.month}>
            <motion.circle
              cx={point.x}
              cy={point.y}
              r={active === index ? 9 : 0}
              fill="#F6E7C8"
              opacity={0.15}
              animate={{ r: active === index ? 9 : 0 }}
            />

            <motion.circle
              cx={point.x}
              cy={point.y}
              r={4}
              fill="#F6E7C8"
              whileHover={{ scale: 1.6 }}
              onMouseEnter={() => setActive(index)}
            />

            {active === index && (
              <g>
                <rect
                  x={point.x - 24}
                  y={point.y - 34}
                  width="48"
                  height="18"
                  rx="9"
                  fill="#202020"
                  stroke="rgba(255,255,255,0.12)"
                />
                <text
                  x={point.x}
                  y={point.y - 22}
                  textAnchor="middle"
                  fontSize="6"
                  fill="#F6E7C8"
                >
                  {point.month}
                </text>
              </g>
            )}

            <text
              x={point.x}
              y="170"
              textAnchor="middle"
              fontSize="6"
              fill="#777"
            >
              {point.month}
            </text>
          </g>
        ))}
      </svg>
    </div>
  );
}