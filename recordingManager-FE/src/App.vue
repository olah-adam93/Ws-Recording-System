<template>
  <div id="app">
    <h1 class="title">WebSocket Client</h1>

    <ConnectionControls
      :isConnected="isConnected"
      @connect="connectToWebSocket"
      @disconnect="disconnectFromWebSocket"
    />

    <RequestButton :isConnected="isConnected" @request="requestRecordings" />

    <RecordingList
      :recordings="recordings"
      @update-recording="updateRecording"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import ConnectionControls from "./components/ConnectionControls.vue";
import RequestButton from "./components/RequestButton.vue";
import RecordingList from "./components/RecordingList.vue";
import { Recording } from "./model/Recording";
import { connect, disconnect, submitRecordings } from "./services/websocket";
import { requestRecordings as fetchRecordings } from "./services/websocket";

export default defineComponent({
  components: { ConnectionControls, RequestButton, RecordingList },

  setup() {
    const isConnected = ref<boolean>(false);
    const recordings = ref<Recording[]>([]);
    const stompClient = ref<any>(null);

    const connectToWebSocket = (): void => {
      if (!stompClient.value) {
        stompClient.value = connect((data: Recording[]) => {
          recordings.value = data.map((recording) => ({ ...recording }));
        });
        isConnected.value = true;
      }
    };

    const disconnectFromWebSocket = (): void => {
      disconnect();
      isConnected.value = false;
      stompClient.value = null;
    };

    const requestRecordings = (): void => {
      if (isConnected.value) {
        fetchRecordings();
      }
    };

    const updateRecording = (
      index: number,
      updatedRecording: Recording
    ): void => {
      recordings.value.splice(index, 1, updatedRecording);
      if (isConnected.value) {
        submitRecordings(updatedRecording);
      }
    };

    return {
      isConnected,
      recordings,
      stompClient,
      connectToWebSocket,
      disconnectFromWebSocket,
      requestRecordings,
      updateRecording,
    };
  },
});
</script>
