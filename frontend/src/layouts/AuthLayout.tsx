import { ReactNode } from "react";
import CinematicBackground from "../components/background/CinematicBackground";

interface Props {
  children: ReactNode;
}

export default function AuthLayout({ children }: Props) {
  return (
    <>
      <CinematicBackground />

      <main className="relative z-10 flex min-h-screen items-center justify-center px-6">
        {children}
      </main>
    </>
  );
}