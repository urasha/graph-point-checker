<script setup>
import {ref, onMounted} from 'vue';
import Canvas from "@/components/Canvas.vue";
import {useRouter} from "vue-router";
import {useAuthenticationStore} from "@/store/authentication.store.js";

const form = ref({x: 1, y: '', r: 2});
const results = ref([]);
const error = ref('');

const router = useRouter();
const authenticationStore = useAuthenticationStore();

const rangeX = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
const rangeR = [0, 0.5, 1, 1.5, 2];

const handleSubmit = async () => {
  if (error.value) {
    return;
  }

  // TODO: try to send point to PointMicroService
}

const loadPreviousResults = async () => {
  try {
    /*
    // TODO:
    // if user is not authenticated -> logout()
    // else: try to load all point from current user into the table
    */
  } catch (err) {
    error.value = 'Ошибка загрузки данных';
  }
};

const logout = () => {
  localStorage.removeItem('jwt');
  authenticationStore.logout();
  router.push('/login');
}

const validateY = () => {
  const y = parseFloat(form.value.y);
  if (isNaN(y) || y < -5 || y > 5) {
    error.value = 'Y должен быть числом в диапазоне -5...5';
  } else {
    error.value = '';
  }
}

onMounted(loadPreviousResults);
</script>

<template>
  <div class="main-page">
    <header>
      <h2>Нестеров Владислав Алексеевич, P3210, вариант: 98743</h2>
      <button class="logout-button" @click="logout">Выйти</button>
    </header>

    <main>
      <section class="input-section">
        <h3>Ввод данных</h3>
        <form @submit.prevent="handleSubmit" class="input-form">
          <label>
            X:
            <select v-model="form.x" required class="select-input">
              <option v-for="option in rangeX" :key="option" :value="option">{{ option }}</option>
            </select>
          </label>

          <label>
            Y:
            <input type="text" v-model="form.y" required @input="validateY" placeholder="-5 ... 5" class="text-input"/>
          </label>

          <label>
            R:
            <select v-model="form.r" required class="select-input">
              <option v-for="option in rangeR" :key="option" :value="option">{{ option }}</option>
            </select>
          </label>
          <button type="submit" class="submit-button">Отправить</button>
        </form>

        <p v-if="error" class="error">{{ error }}</p>
      </section>

      <section class="graph-section">
        <Canvas :rValue="form.r"/>
      </section>
    </main>

    <section class="results-section">
      <h3>Таблица результатов</h3>
      <table class="results-table">
        <thead>
        <tr>
          <th>X</th>
          <th>Y</th>
          <th>R</th>
          <th>Результат</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="result in results" :key="result.id">
          <td>{{ result.x }}</td>
          <td>{{ result.y }}</td>
          <td>{{ result.r }}</td>
          <td>{{ result.hit ? 'Попадание' : 'Промах' }}</td>
        </tr>
        </tbody>
      </table>
    </section>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Handjet:wght@100..900&display=swap');

.main-page {
  font-family: 'Handjet', sans-serif;
  padding: 20px;
  background-color: #f9f9f9;
  color: #333;
  max-width: 900px;
  margin: 0 auto;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #4caf50;
  color: white;
  padding: 10px 20px;
  border-radius: 10px;
  margin-bottom: 20px;
}

.logout-button {
  background-color: #f44336;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 8px 16px;
  cursor: pointer;
}

.logout-button:hover {
  background-color: #d32f2f;
}

main {
  display: flex;
  gap: 20px;
}

main > section {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.input-section {
  flex: 2;
}

.graph-section {
  flex: 1;
  text-align: center
}

.input-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.select-input, .text-input, .submit-button {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.submit-button {
  background-color: #4caf50;
  color: white;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #388e3c;
}

.error {
  color: red;
  font-weight: bold;
}

.results-section {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  margin-top: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.results-table {
  width: 100%;
  border-collapse: collapse;
}

.results-table th, .results-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

.results-table th {
  background-color: #4caf50;
  color: white;
}

.results-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.results-table tr:hover {
  background-color: #f1f1f1;
}
</style>
