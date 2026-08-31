import { useEffect, useState } from "react";
import { Bell, CheckCircle2, Clock } from "lucide-react";

import Navbar from "../../components/navigation/Navbar";
import { getNotifications } from "../../api/notification";
import type { Notification } from "../../types/notification";

export default function Notifications() {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadNotifications() {
      try {
        const data = await getNotifications();
        setNotifications(data);
      } catch {
        setError("Unable to load notifications");
      } finally {
        setLoading(false);
      }
    }

    loadNotifications();
  }, []);

  return (
    <>
      <Navbar />

      <main className="min-h-screen px-6 pt-32 pb-12">
        <div className="mx-auto max-w-5xl">
          {/* Header */}
          <div className="mb-10 flex items-center gap-4">
            <div className="rounded-2xl bg-[#F6E7C8]/10 p-3">
              <Bell className="h-7 w-7 text-[#F6E7C8]" />
            </div>

            <div>
              <h1 className="text-4xl font-light text-white">
                Notifications
              </h1>
              <p className="mt-1 text-white/60">
                Recent updates from your AXION account
              </p>
            </div>
          </div>

          {/* Loading */}
          {loading && (
            <div className="rounded-3xl border border-white/10 bg-white/5 p-8 text-center backdrop-blur-xl">
              <p className="text-white/70">Loading notifications...</p>
            </div>
          )}

          {/* Error */}
          {!loading && error && (
            <div className="rounded-3xl border border-red-500/20 bg-red-500/10 p-8 text-center">
              <p className="text-red-300">{error}</p>
            </div>
          )}

          {/* Empty */}
          {!loading && !error && notifications.length === 0 && (
            <div className="rounded-3xl border border-white/10 bg-white/5 p-10 text-center backdrop-blur-xl">
              <Bell className="mx-auto mb-4 h-12 w-12 text-white/30" />
              <h3 className="text-xl text-white">No notifications yet</h3>
              <p className="mt-2 text-white/50">
                You'll see loan, portfolio, and repayment updates here.
              </p>
            </div>
          )}

          {/* Notification List */}
          <div className="space-y-4">
            {notifications.map((notification) => (
              <div
                key={notification.id}
                className="rounded-3xl border border-white/10 bg-white/5 p-6 backdrop-blur-xl transition hover:border-[#F6E7C8]/20"
              >
                <div className="flex items-start gap-4">
                  <div className="mt-1 rounded-full bg-emerald-400/10 p-2">
                    <CheckCircle2 className="h-5 w-5 text-emerald-300" />
                  </div>

                  <div className="min-w-0 flex-1">
                    <div className="flex flex-wrap items-center justify-between gap-2">
                      <h3 className="text-lg font-medium text-white">
                        {notification.title}
                      </h3>

                      <span className="rounded-full bg-white/10 px-3 py-1 text-xs uppercase tracking-wide text-white/60">
                        {notification.type ?? "Notification"}
                      </span>
                    </div>

                    <p className="mt-2 leading-7 text-white/70">
                      {notification.message}
                    </p>

                    <div className="mt-4 flex items-center gap-2 text-sm text-white/40">
                      <Clock className="h-4 w-4" />
                      {notification.sentAt
                        ? new Date(notification.sentAt).toLocaleString()
                        : "Just now"}
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </main>
    </>
  );
}
