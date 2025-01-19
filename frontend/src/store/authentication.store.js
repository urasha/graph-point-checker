import { defineStore } from 'pinia';

export const useAuthenticationStore = defineStore('authentication', {
  state: () => ({
    isAuthenticated: true,
  }),

  actions: {
    login() {
      this.isAuthenticated = true;
    },
    logout() {
      this.isAuthenticated = false;
    },
  },

  persist: true
});
