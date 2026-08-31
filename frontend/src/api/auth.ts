import api from "./axios";
import type { LoginRequest, LoginResponse } from "../types/auth";

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
}

export async function register(data: RegisterRequest): Promise<void> {
  await api.post("/auth/register", data);
}

export async function login(data: LoginRequest) {
  const response = await api.post<LoginResponse>(
    "/auth/login",
    data
  );

  localStorage.setItem(
    "axion_token",
    response.data.accessToken
  );
  localStorage.setItem("axion_refresh_token", response.data.refreshToken);
  localStorage.setItem("axion_user_id", response.data.user.id);

  return response.data;
}
