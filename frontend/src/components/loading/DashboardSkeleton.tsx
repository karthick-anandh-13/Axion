import GlassCard from "../ui/GlassCard";
import Skeleton from "./Skeleton";

export default function DashboardSkeleton() {
  return (
    <div className="space-y-6">
      <Skeleton className="h-10 w-72" />

      <div className="grid gap-5 md:grid-cols-2 xl:grid-cols-4">
        {[1, 2, 3, 4].map((i) => (
          <GlassCard key={i} className="p-6">
            <Skeleton className="mb-3 h-4 w-24" />
            <Skeleton className="h-8 w-32" />
          </GlassCard>
        ))}
      </div>

      <GlassCard className="p-6">
        <Skeleton className="mb-5 h-6 w-48" />
        <Skeleton className="h-64 w-full" />
      </GlassCard>
    </div>
  );
}