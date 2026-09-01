import { Check, Clock } from "lucide-react";
import type { TransferStep } from "../../types/payment";

interface ProgressTimelineProps {
  steps: TransferStep[];
}

export default function ProgressTimeline({ steps }: ProgressTimelineProps) {
  return (
    <div className="flex items-center justify-between gap-2 overflow-x-auto pb-4">
      {steps.map((step, index) => (
        <div key={step.id} className="flex items-center gap-2">
          {/* Step circle */}
          <div
            className={`relative flex h-10 w-10 items-center justify-center rounded-full text-sm font-semibold transition-all ${
              step.completed
                ? "bg-green-500/20 text-green-300"
                : step.current
                  ? "border-2 border-[#C7F5D9] bg-[#C7F5D9]/10 text-[#C7F5D9]"
                  : "border border-white/20 bg-white/5 text-white/40"
            }`}
          >
            {step.completed ? (
              <Check size={20} />
            ) : step.current ? (
              <Clock size={20} className="animate-spin" />
            ) : (
              index + 1
            )}
          </div>

          {/* Step label (only on desktop) */}
          <span
            className={`hidden min-w-max text-xs transition-colors sm:inline ${
              step.completed || step.current ? "text-white" : "text-white/40"
            }`}
          >
            {step.label}
          </span>

          {/* Connector line */}
          {index < steps.length - 1 && (
            <div
              className={`ml-2 h-1 w-8 rounded-full transition-colors ${
                step.completed ? "bg-green-500/40" : "bg-white/10"
              }`}
            />
          )}
        </div>
      ))}
    </div>
  );
}
