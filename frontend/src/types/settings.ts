export interface UserSettings {
  fullName: string;
  email: string;
  phone: string;

  biometric: boolean;
  autoPay: boolean;
  darkMode: boolean;

  kycVerified: boolean;
}