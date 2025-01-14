<script setup>
import {ref} from 'vue';
import axios from "axios";
import {useRouter} from "vue-router";
import {useAuthenticationStore} from "@/store/authentication.store.js";

const username = ref('');
const password = ref('');

const router = useRouter();
const authenticationStore = useAuthenticationStore();

const handleLogin = async () => {
  try {
    const response = await axios.post('/api/users/login', {
      username: username.value,
      password: password.value,
    });

    if (response.status === 200) {
      const token = response.data;

      console.log('Login successful:', token);

      localStorage.setItem('jwt', token);
      authenticationStore.login();

      await router.push('/');
    }
  } catch (error) {
    if (error.response) {
      console.error('Login failed: ', error.response.data);
    } else {
      console.error('Error during login: ', error.message);
    }
  }
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
  margin: 50px auto;
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

.register-link a:hover {
  text-decoration: underline;
}
</style>
