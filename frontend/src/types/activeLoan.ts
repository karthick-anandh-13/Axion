export interface ActiveLoan {
  id: string;
  title: string;
  principal: number;
  emi: number;
  progress: number;
  nextDue: string;
}