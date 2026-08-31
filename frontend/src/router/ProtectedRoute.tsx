import { Navigate } from "react-router-dom";
import type { ReactElement } from "react";
import { isAuthenticated } from "../hooks/useAuth";

export default function ProtectedRoute({
  children,
}: {
  children: ReactElement;
}) {
  if (!isAuthenticated()) {
    return <Navigate to="/" replace />;
  }

  return children;
}
