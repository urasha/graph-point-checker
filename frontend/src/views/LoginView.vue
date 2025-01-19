<script setup>
import {ref} from 'vue';
import axios from "axios";
import {useRouter} from "vue-router";
import {useAuthenticationStore} from "@/store/authentication.store.js";

const username = ref('');
const password = ref('');
const errorMessage = ref('');

const router = useRouter();
const authenticationStore = useAuthenticationStore();

const handleLogin = async () => {
  axios.post('/api/users/login', {
    username: username.value,
    password: password.value,
  }).then((response) => {
    const {jwt} = response.data;

    console.log(`Login successful: ${jwt}`);

    localStorage.setItem('jwt', jwt);
    authenticationStore.login();

    router.push('/');
  }).catch((error) => {
    const {message} = error.response.data;
    errorMessage.value = message;
    console.error(`Login failed: ${message}`);
  })
};
</script>

<template>
  <div class="login-view">
    <h1>Вход</h1>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Имя пользователя</label>
        <input type="text" id="username" v-model.trim="username" placeholder="Введите имя пользователя" required/>
      </div>

      <div class="form-group">
        <label for="password">Пароль</label>
        <input type="password" id="password" v-model.trim="password" placeholder="Введите пароль" required/>
      </div>

      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>

      <button type="submit" class="btn btn-primary">Войти</button>
    </form>

    <div class="register-link">
      <p>Ещё нет аккаунта?</p>
      <RouterLink to="/register">Создать аккаунт</RouterLink>
    </div>
  </div>
</template>


<style scoped>
.login-view {
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.register-link {
  text-align: center;
  margin-top: 15px;
}

.register-link a {
  color: #007bff;
  text-decoration: none;
}

.error-message {
  color: red;
  font-size: 0.9rem;
  margin-bottom: 15px;
}

@media (min-width: 892px) {
  .login-view {
    margin: 30px auto;
  }
  h1 {
    font-size: 32px;
  }
  input, button {
    font-size: 16px;
  }
}

@media (min-width: 1202px) {
  .login-view {
    margin: 50px auto;
  }
  h1 {
    font-size: 32px;
  }
  input, button {
    font-size: 16px;
  }
}
</style>
