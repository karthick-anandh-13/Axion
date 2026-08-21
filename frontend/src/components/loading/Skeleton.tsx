interface Props {
  className?: string;
}

export default function Skeleton({ className = "" }: Props) {
  return (
    <div
      className={`animate-pulse rounded-xl bg-linear-to-r from-white/5 via-white/10 to-white/5 ${className}`}
    />
  );
}