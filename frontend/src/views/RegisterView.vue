<script setup>
import { ref } from 'vue';

const username = ref('');
const password = ref('');
const confirmPassword = ref('');

const handleRegister = () => {
  if (password.value !== confirmPassword.value) {
    console.log('Passwords do not match');
    return;
  }

  tryRegister();
};

const tryRegister = () => {
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

  fetch('/api/auth/register', request)
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      console.log('Registration successful:', data);
      // TODO: redirect to login page or show success message
    })
    .catch(error => {
      console.error('Registration error:', error);
    });
}
</script>

<template>
  <div class="register-view">
    <h1>Регистрация</h1>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="username">Имя пользователя</label>
        <input type="text" id="username" v-model.trim="username" placeholder="Введите имя пользователя" required />
      </div>

      <div class="form-group">
        <label for="password">Пароль</label>
        <input type="password" id="password" v-model.trim="password" placeholder="Введите пароль" required />
      </div>

      <div class="form-group">
        <label for="confirm-password">Подтвердите пароль</label>
        <input type="password" id="confirm-password" v-model.trim="confirmPassword" placeholder="Введите пароль ещё раз" required />
      </div>

      <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
    </form>

    <div class="login-link">
      <p>Уже есть аккаунт?</p>
      <RouterLink to="/login">Войти</RouterLink>
    </div>
  </div>
</template>

<style scoped>
.register-view {
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

.login-link {
  text-align: center;
  margin-top: 15px;
}

.login-link a {
  color: #007bff;
  text-decoration: none;
}

.login-link a:hover {
  text-decoration: underline;
}
</style>
