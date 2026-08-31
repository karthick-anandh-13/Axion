import type { ReactNode } from "react";
import GlassCard from "../ui/GlassCard";

interface Props {
  title: string;
  description?: string;
  children: ReactNode;
  className?: string;
}

export default function SectionCard({ title, description, children, className }: Props) {
  return (
    <GlassCard className={`p-6 ${className ?? ""}`}>
      <h2 className="text-lg font-medium text-white">{title}</h2>
      {description && <p className="mt-1 text-sm text-white/50">{description}</p>}
      <div className="mt-5">{children}</div>
    </GlassCard>
  );
}
