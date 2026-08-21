import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getNotifications, markAsRead } from "../api/notification";

export function useNotifications() {
  const qc = useQueryClient();

  const query = useQuery({
    queryKey: ["notifications"],
    queryFn: getNotifications,
  });

  const readMutation = useMutation({
    mutationFn: markAsRead,

    onSuccess: () => {
      qc.invalidateQueries({
        queryKey: ["notifications"],
      });
    },
  });

  return { ...query, readMutation };
}