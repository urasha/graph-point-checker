<script setup>
import {ref} from 'vue';
import axios from "axios";
import {useRouter} from "vue-router";

const username = ref('');
const password = ref('');
const confirmPassword = ref('');
const errorMessage = ref('');

const router = useRouter();

const handleRegister = async () => {
  if (checkPasswordIdentity() === false) {
    errorMessage.value = 'Пароли не совпадают!';
    return
  }

  if (validateFormValues() === false) {
    errorMessage.value = 'Логин и пароль не могут быть пустыми!'
    return;
  }

  await tryRegister();
};

const checkPasswordIdentity = () => {
  return password.value === confirmPassword.value;
}

const validateFormValues = () => {
  return username.value && password.value;
};

const tryRegister = async () => {
  axios.post('/api/users/register', {
    username: username.value,
    password: password.value
  })
    .then(response => {
      console.log('Registration successful');
      router.push('/login');
    })
    .catch(error => {
      if (error.response.status === 401) {
        const {message} = error.response.data;
        errorMessage.value = message;
        console.error(`Server error: ${message}`);
      } else {
        console.error(`Network or other error: ${error.message}`);
      }
    })
};
</script>

<template>
  <div class="register-view">
    <h1>Регистрация</h1>
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="username">Имя пользователя</label>
        <input type="text" id="username" v-model.trim="username" placeholder="Введите имя пользователя" required/>
      </div>

      <div class="form-group">
        <label for="password">Пароль</label>
        <input type="password" id="password" v-model.trim="password" placeholder="Введите пароль" required/>
      </div>

      <div class="form-group">
        <label for="confirm-password">Подтвердите пароль</label>
        <input type="password" id="confirm-password" v-model.trim="confirmPassword" placeholder="Введите пароль ещё раз"
               required/>
      </div>

      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
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

.error-message {
  color: red;
  font-size: 0.9rem;
  margin-bottom: 15px;
}

@media (min-width: 892px) and (max-width: 1201px) {
  .register-view {
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
  .register-view {
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
