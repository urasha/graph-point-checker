<script setup>
import { useAuthenticationStore } from '@/store/authentication-store';
import { useRouter } from 'vue-router';
import { onBeforeMount } from 'vue';

const store = useAuthenticationStore();
const router = useRouter();

onBeforeMount(() => {
  if (!store.isAuthenticated) {
    router.push('/login');
  }
});

const props = defineProps({
  view: {
    type: Object,
    required: true
  }
});
</script>

<template>
  <component :is="props.view"></component>
</template>
