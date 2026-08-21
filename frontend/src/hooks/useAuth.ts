export function isAuthenticated() {
  return !!localStorage.getItem("axion_token");
}