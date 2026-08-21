export interface Notification {
  id: string;

  title: string;

  message: string;

  category: "PAYMENT" | "AI" | "MARKETPLACE" | "SECURITY";

  read: boolean;

  createdAt: string;
}