<template>
  <div class="controls">
    <button @click="$emit('connect')" :disabled="isConnected">Connect</button>
    <button @click="$emit('disconnect')" :disabled="!isConnected">
      Disconnect
    </button>
  </div>
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
  emits: ["connect", "disconnect"],
});
</script>
