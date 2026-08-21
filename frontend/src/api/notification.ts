import api from "./axios";
import type { Notification } from "../types/notification";

export async function getNotifications() {
  const res = await api.get<Notification[]>("/v1/notifications");
  return res.data;
}

export async function markAsRead(id: string) {
  await api.patch(`/v1/notifications/${id}/read`);
}