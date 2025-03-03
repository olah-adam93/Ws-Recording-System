<template>
  <li class="recording-item">
    <template v-if="isEditing">
      <form v-if="localRecording" @submit.prevent="submitEdit">
        <div class="form-group">
          <label>Title:</label>
          <input v-model="localRecording.title" type="text" required />
        </div>
        <div class="form-group">
          <label>Status:</label>
          <select v-model="localRecording.status" required>
            <option value="SCHEDULED">Scheduled</option>
            <option value="RECORDED">Recorded</option>
            <option value="REPORTED">Reported</option>
          </select>
        </div>
        <div class="form-group">
          <label>Duration (seconds):</label>
          <input
            v-model.number="localRecording.duration"
            type="number"
            required
          />
        </div>
        <div class="form-group">
          <label>Sedation:</label>
          <input v-model="localRecording.sedation" type="text" required />
        </div>
        <div class="form-group">
          <label>Activation:</label>
          <input v-model="localRecording.activation" type="text" required />
        </div>
        <div class="form-group">
          <label>Medication:</label>
          <input v-model="localRecording.medication" type="text" required />
        </div>

        <button type="submit">Save</button>
        <button type="button" @click="cancelEdit">Cancel</button>
      </form>
    </template>

    <template v-else>
      <div><strong>Title:</strong> {{ recording.title }}</div>
      <div><strong>Status:</strong> {{ recording.status }}</div>
      <div><strong>Duration:</strong> {{ recording.duration }} seconds</div>
      <div><strong>Sedation:</strong> {{ recording.sedation }}</div>
      <div><strong>Activation:</strong> {{ recording.activation }}</div>
      <div><strong>Medication:</strong> {{ recording.medication }}</div>

      <button v-if="recording.status === 'RECORDED'" @click="startEdit">
        Edit
      </button>
    </template>
  </li>
</template>

<script lang="ts">
import { defineComponent, PropType } from "vue";
import { Recording } from "../model/Recording";

export default defineComponent({
  props: {
    recording: {
      type: Object as PropType<Recording>,
      required: true,
    },
  },
  emits: ["update"],
  data() {
    return {
      isEditing: false,
      localRecording: null as Recording | null,
    };
  },
  methods: {
    startEdit() {
      this.isEditing = true;
      this.localRecording = { ...this.recording };
    },
    cancelEdit() {
      this.isEditing = false;
      this.localRecording = null;
    },
    submitEdit() {
      if (this.localRecording) {
        this.$emit("update", this.localRecording);
        this.cancelEdit();
      }
    },
  },
});
</script>
