import api from "./axios";
import type { Notification } from "../types/notification";

export async function getNotifications(): Promise<Notification[]> {
  const response = await api.get<Notification[]>("/v1/notifications");
  return response.data;
}

export async function markAsRead(id: string): Promise<void> {
  await api.patch(`/v1/notifications/${id}/read`);
}
