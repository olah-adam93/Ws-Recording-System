<template>
  <div v-if="recordings.length > 0" class="recordings">
    <h2>Recordings:</h2>
    <ul>
      <RecordingItem
        v-for="(recording, index) in recordings"
        :key="index"
        :recording="recording"
        @update="updateRecording(index, $event)"
      />
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from "vue";
import { Recording } from "../model/Recording";
import RecordingItem from "./RecordingItem.vue";

export default defineComponent({
  components: { RecordingItem },
  props: {
    recordings: {
      type: Array as PropType<Recording[]>,
      required: true,
    },
  },
  emits: ["update-recording"],
  methods: {
    updateRecording(index: number, updatedRecording: Recording) {
      this.$emit("update-recording", index, updatedRecording);
    },
  },
});
</script>
