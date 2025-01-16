import { defineStore } from 'pinia';

export const useAuthenticationStore = defineStore('authentication', {
  state: () => ({
    isAuthenticated: false, // TODO: SET FALSE IN PRODUCTION
  }),
  actions: {
    login() {
      this.isAuthenticated = true;
    },
    logout() {
      this.isAuthenticated = false;
    },
  },
});
