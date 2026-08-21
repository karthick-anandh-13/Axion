import api from "./axios";
import type { UserSettings } from "../types/settings";

export async function getSettings() {
  const res = await api.get<UserSettings>("/v1/settings");
  return res.data;
}

export async function updateSettings(data: UserSettings) {
  await api.put("/v1/settings", data);
}