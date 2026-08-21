import { create } from "zustand";

interface LoadingStore {
  loading: boolean;
  start: () => void;
  stop: () => void;
}

export const useLoading = create<LoadingStore>((set) => ({
  loading: false,

  start: () => set({ loading: true }),

  stop: () => set({ loading: false }),
}));