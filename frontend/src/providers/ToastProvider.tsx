import type { ReactNode } from "react";
import ToastContainer from "../components/toast/ToastContainer";

export default function ToastProvider({ children }: { children: ReactNode }) {
  return (
    <>
      {children}
      <ToastContainer />
    </>
  );
}
