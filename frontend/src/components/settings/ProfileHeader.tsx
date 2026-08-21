import GlassCard from "../ui/GlassCard";
import { ShieldCheck } from "lucide-react";

interface Props {
  name: string;
  email: string;
}

export default function ProfileHeader({
  name,
  email,
}: Props) {
  const initials = name
    .split(" ")
    .map((x) => x[0])
    .join("");

  return (
    <GlassCard className="p-8">
      <div className="flex flex-col items-center">
        <div className="flex h-24 w-24 items-center justify-center rounded-full bg-linear-to-br from-[#F6E7C8]/25 to-[#C7F5D9]/10 text-3xl font-light text-[#F6E7C8]">
          {initials}
        </div>

        <h2 className="mt-5 text-3xl font-light text-white">
          {name}
        </h2>

        <p className="mt-1 text-white/45">{email}</p>

        <div className="mt-4 flex items-center gap-2 rounded-full bg-[#C7F5D9]/10 px-4 py-2 text-[#C7F5D9]">
          <ShieldCheck size={16} />
          <span className="text-sm">KYC Verified</span>
        </div>
      </div>
    </GlassCard>
  );
}