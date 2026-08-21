import type { ReactNode } from "react";

import CinematicBackground from "../components/background/CinematicBackground";
import AmbientBackground from "../components/animation/AmbientBackground";
import Sidebar from "../components/dashboard/Sidebar";
import Topbar from "../components/dashboard/Topbar";
import NotificationBell from "../components/notification/NotificationBell";

interface Props {
  children: ReactNode;
}

export default function DashboardLayout({ children }: Props) {
  return (
    <>
      {/* Global Background Layers */}
      <CinematicBackground />
      <AmbientBackground />

      {/* App */}
      <div className="relative z-10 flex min-h-screen">
        {/* Sidebar */}
        <Sidebar />

        {/* Main Content */}
        <main className="flex-1 overflow-x-hidden px-8 py-6">
          {/* Header */}
          <header className="mb-8 flex items-center justify-between">
            <Topbar />

            <div className="flex items-center gap-4">
              <NotificationBell />

              <div className="h-11 w-11 overflow-hidden rounded-full border border-white/10 bg-white/5 shadow-[0_0_20px_rgba(246,231,200,0.08)]">
                <img
                  src="/avatar.png"
                  alt="Profile"
                  className="h-full w-full object-cover"
                />
              </div>
            </div>
          </header>

          {/* Page Content */}
          <div className="animate-in fade-in duration-500">
            {children}
          </div>
        </main>
      </div>
    </>
  );
}