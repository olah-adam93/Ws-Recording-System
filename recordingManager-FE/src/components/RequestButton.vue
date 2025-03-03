<template>
  <button
    @click="$emit('request')"
    :disabled="!isConnected"
    class="request-button"
  >
    Request Recordings
  </button>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    isConnected: {
      type: Boolean,
      required: true,
    },
  },
  emits: ["request"],
});
</script>
