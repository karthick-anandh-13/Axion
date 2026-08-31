import axios from "axios";

const api = axios.create({
  baseURL:
    import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8081/api",
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("axion_token");
    const userId = localStorage.getItem("axion_user_id");

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    if (userId) {
      config.headers["X-User-Id"] = userId;
    }

    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem("axion_token");
      localStorage.removeItem("axion_user_id");
      window.location.href = "/login";
    }

    return Promise.reject(error);
  }
);

export default api;