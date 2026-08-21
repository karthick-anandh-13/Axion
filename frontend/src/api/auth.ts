import api from "./axios";
import type { LoginRequest, LoginResponse } from "../types/auth";

export async function login(data: LoginRequest) {
  const response = await api.post<LoginResponse>(
    "/auth/login",
    data
  );

  localStorage.setItem(
    "axion_token",
    response.data.token
  );

  return response.data;
}