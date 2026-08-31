import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getNotifications, markAsRead } from "../api/notification";

export function useNotifications() {
  const queryClient = useQueryClient();

  const query = useQuery({
    queryKey: ["notifications"],
    queryFn: getNotifications,
  });

  const readMutation = useMutation({
    mutationFn: markAsRead,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["notifications"],
      });
    },
  });

  return {
    ...query,
    readMutation,
  };
}
