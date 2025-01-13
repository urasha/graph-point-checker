<script setup>
import { ref } from 'vue';

const username = ref('');
const password = ref('');

const handleLogin = () => {
  // TODO: remove logs
  console.log("Username:", username.value);
  console.log("Password:", password.value);

  tryLogin();
};

const tryLogin = () => {
  const request = {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(
      {
        'username': username.value,
        'password': password.value
      })
  };

  fetch('/api/auth/login', request)
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      console.log('Login response:', data);
      // TODO: get JWT and save it
    })
    .catch(error => {
      console.error(error);
    });
}
</script>

<template>
  <div class="login-view">
    <h1>Вход</h1>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Имя пользователя</label>
        <input type="text" id="username" v-model.trim="username" placeholder="Введите имя пользователя" required />
      </div>

      <div class="form-group">
        <label for="password">Пароль</label>
        <input type="password" id="password" v-model.trim="password" placeholder="Введите пароль" required />
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
