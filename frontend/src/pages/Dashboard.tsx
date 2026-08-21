import CinematicBackground from "../components/background/CinematicBackground";
import Navbar from "../components/navigation/Navbar";

export default function Dashboard() {
  return (
    <>
      <CinematicBackground />
      <Navbar />

      <main className="relative z-10 flex min-h-screen items-center justify-center">
        <div className="text-center">
          <h1 className="text-6xl font-light text-[#F6E7C8]">
            AXION
          </h1>

          <p className="mt-4 text-white/50">
            Dashboard coming next
          </p>
        </div>
      </main>
    </>
  );
}