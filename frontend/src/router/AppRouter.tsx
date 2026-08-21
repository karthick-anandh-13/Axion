import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";
import ForgotPassword from "../pages/auth/ForgotPassword";
import VerifyOTP from "../pages/auth/VerifyOTP";
import Welcome from "../pages/onboarding/Welcome";
import PersonalInfo from "../pages/onboarding/PersonalInfo";
import Employment from "../pages/onboarding/Employment";
import Income from "../pages/onboarding/Income";
import Goals from "../pages/onboarding/Goals";
import KYC from "../pages/onboarding/KYC";
import AIProfile from "../pages/onboarding/AIProfile";
import Dashboard from "../pages/Dashboard";
import Home from "../pages/dashboard/Home";
import Borrow from "../pages/borrow/Borrow";
import Portfolio from "../pages/portfolio/Portfolio";
import Settings from "../pages/settings/Settings";
import Admin from "../pages/admin/Admin";

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot" element={<ForgotPassword />} />
        <Route path="/verify" element={<VerifyOTP />} />
        <Route path="/onboarding" element={<Welcome />} />
        <Route path="/onboarding/personal" element={<PersonalInfo />} />
        <Route path="/onboarding/employment" element={<Employment />}/>
        <Route path="/onboarding/income" element={<Income />} />
        <Route path="/onboarding/goals" element={<Goals />} />
        <Route path="/onboarding/kyc" element={<KYC />} />
        <Route path="/onboarding/ai" element={<AIProfile />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/dashboard" element={<Home />} />
        <Route path="/borrow" element={<Borrow />} />
        <Route path="/portfolio" element={<Portfolio />} />
        <Route path="/settings" element={<Settings />} />
        <Route path="/admin" element={<Admin />} />
      </Routes>
    </BrowserRouter>
  );
}