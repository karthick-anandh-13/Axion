import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getSettings, updateSettings } from "../api/settings";

export function useSettings() {
  const qc = useQueryClient();

  const query = useQuery({
    queryKey: ["settings"],
    queryFn: getSettings,
  });

  const mutation = useMutation({
    mutationFn: updateSettings,

    onSuccess: () => {
      qc.invalidateQueries({
        queryKey: ["settings"],
      });
    },
  });

  return { ...query, mutation };
}