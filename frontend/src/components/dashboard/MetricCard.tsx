import GlassCard from "../ui/GlassCard";

interface Props {
  title: string;
  value: string;
  change: string;
}

export default function MetricCard({
  title,
  value,
  change,
}: Props) {
  return (
    <GlassCard className="p-6">
      <p className="text-white/40 text-sm">{title}</p>

      <h3 className="mt-3 text-3xl font-light text-[#F6E7C8]">
        {value}
      </h3>

      <p className="mt-4 text-[#C7F5D9] text-sm">{change}</p>
    </GlassCard>
  );
}