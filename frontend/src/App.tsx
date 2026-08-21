import { AnimatePresence } from "framer-motion";
import { Routes, Route, useLocation } from "react-router-dom";

import CinematicBackground from "./components/background/CinematicBackground";
import PageTransition from "./components/animation/PageTransition";
import CommandPalette from "./components/command/CommandPalette";
import ToastContainer from "./components/toast/ToastContainer";
// Landing sections
import Navbar from "./components/navigation/Navbar";
import Hero from "./components/sections/Hero";
import FeatureShowcase from "./components/sections/FeatureShowcase";
import DashboardPreview from "./components/sections/DashboardPreview";
import TrustSecurity from "./components/sections/TrustSecurity";

// Pages
import SplashScreen from "./pages/splash/SplashScreen";
import Borrow from "./pages/borrow/Borrow";
import Portfolio from "./pages/portfolio/Portfolio";
import Marketplace from "./pages/marketplace/Marketplace";
import CreditIntelligence from "./pages/ai/CreditIntelligence";
import Repayment from "./pages/repayment/Repayment";
import Settings from "./pages/settings/Settings";

function LandingPage() {
  return (
    <>
      <Navbar />
      <Hero />
      <FeatureShowcase />
      <DashboardPreview />
      <TrustSecurity />
    </>
  );
}

export default function App() {
  const location = useLocation();

  return (
    <>
      {/* Global Background */}
      <CinematicBackground />

      {/* Global Components */}
      <CommandPalette />
      <ToastContainer />

      {/* Animated Routes */}
      <AnimatePresence mode="wait">
        <Routes location={location} key={location.pathname}>
          {/* Splash */}
          <Route
            path="/"
            element={
              <PageTransition>
                <SplashScreen />
              </PageTransition>
            }
          />

          {/* Marketing Landing */}
          <Route
            path="/home"
            element={
              <PageTransition>
                <LandingPage />
              </PageTransition>
            }
          />

          {/* Borrow */}
          <Route
            path="/borrow"
            element={
              <PageTransition>
                <Borrow />
              </PageTransition>
            }
          />

          {/* Portfolio */}
          <Route
            path="/portfolio"
            element={
              <PageTransition>
                <Portfolio />
              </PageTransition>
            }
          />

          {/* Marketplace */}
          <Route
            path="/marketplace"
            element={
              <PageTransition>
                <Marketplace />
              </PageTransition>
            }
          />

          {/* Repayment */}
          <Route
            path="/repayment"
            element={
              <PageTransition>
                <Repayment />
              </PageTransition>
            }
          />

          {/* AI Intelligence */}
          <Route
            path="/ai"
            element={
              <PageTransition>
                <CreditIntelligence />
              </PageTransition>
            }
          />

          {/* Settings */}
          <Route
            path="/settings"
            element={
              <PageTransition>
                <Settings />
              </PageTransition>
            }
          />
        </Routes>
      </AnimatePresence>
    </>
  );
}