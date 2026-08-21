import { Navigate } from "react-router-dom";
import { isAuthenticated } from "../hooks/useAuth";

export default function ProtectedRoute({
  children,
}: {
  children: JSX.Element;
}) {
  if (!isAuthenticated()) {
    return <Navigate to="/" replace />;
  }

  return children;
}