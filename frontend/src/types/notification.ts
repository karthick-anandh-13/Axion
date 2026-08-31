export interface Notification {
  id: string;

  title: string;

  message: string;

  type: "EMI_REMINDER" | "EMI_RECEIPT" | "LOAN_DISBURSED" | "OFFER_EXPIRED" | "LOAN_APPROVED" | "OVERDUE_ALERT";
  status: "PENDING" | "SENT" | "READ" | "FAILED";
  sentAt: string;
}
