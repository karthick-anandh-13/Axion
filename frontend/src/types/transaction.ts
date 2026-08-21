export interface Transaction {
  id: string;
  title: string;
  amount: number;
  type: "CREDIT" | "DEBIT";
  category: string;
  createdAt: string;
}