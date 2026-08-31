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
import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";
import ForgotPassword from "./pages/auth/ForgotPassword";
import VerifyOTP from "./pages/auth/verifyOTP";
import Welcome from "./pages/onboarding/Welcome";
import PersonalInfo from "./pages/onboarding/PersonalInfo";
import Employment from "./pages/onboarding/Employment";
import Income from "./pages/onboarding/Income";
import Goals from "./pages/onboarding/Goals";
import KYC from "./pages/onboarding/KYC";
import AIProfile from "./pages/onboarding/AIProfile";
import Notifications from "./pages/notifications/Notifications";
import ProtectedRoute from "./router/ProtectedRoute";

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

function DashboardPage() {
  return (
    <>
      <Navbar />
      <DashboardPreview />
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

          <Route path="/login" element={<PageTransition><Login /></PageTransition>} />
          <Route path="/register" element={<PageTransition><Register /></PageTransition>} />
          <Route path="/forgot" element={<PageTransition><ForgotPassword /></PageTransition>} />
          <Route path="/verify" element={<PageTransition><VerifyOTP /></PageTransition>} />
          <Route path="/onboarding" element={<ProtectedRoute><Welcome /></ProtectedRoute>} />
          <Route path="/onboarding/personal" element={<ProtectedRoute><PersonalInfo /></ProtectedRoute>} />
          <Route path="/onboarding/employment" element={<ProtectedRoute><Employment /></ProtectedRoute>} />
          <Route path="/onboarding/income" element={<ProtectedRoute><Income /></ProtectedRoute>} />
          <Route path="/onboarding/goals" element={<ProtectedRoute><Goals /></ProtectedRoute>} />
          <Route path="/onboarding/kyc" element={<ProtectedRoute><KYC /></ProtectedRoute>} />
          <Route path="/onboarding/ai" element={<ProtectedRoute><AIProfile /></ProtectedRoute>} />

          {/* Landing */}
          <Route
            path="/home"
            element={
              <PageTransition>
                <LandingPage />
              </PageTransition>
            }
          />

          {/* Dashboard */}
          <Route
            path="/dashboard"
            element={
              <PageTransition>
                <DashboardPage />
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

          {/* Lend (Alias) */}
          <Route
            path="/lend"
            element={
              <PageTransition>
                <Marketplace />
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

          {/* Portfolio */}
          <Route
            path="/portfolio"
            element={
              <PageTransition>
                <Portfolio />
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

          {/* AI */}
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
          <Route path="/notifications" element={<ProtectedRoute><Notifications /></ProtectedRoute>} />
        </Routes>
      </AnimatePresence>
    </>
  );
}
