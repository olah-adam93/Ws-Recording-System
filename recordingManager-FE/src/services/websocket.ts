/* eslint-disable no-unused-vars */
import { Recording } from "@/model/Recording";
import { Client, IMessage } from "@stomp/stompjs";
import SockJS from "sockjs-client";

let stompClient: Client | null = null;

export function connect(callback: (data: Recording[]) => void): void {
  const socket = new SockJS("http://localhost:8080/ws");

  stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
    debug: (message: string) => console.log("STOMP debug:", message),

    onConnect: () => {
      console.log("STOMP client connected.");

      stompClient?.subscribe("/topic/recordings", (message: IMessage) => {
        let body: string;

        if (message.isBinaryBody) {
          body = new TextDecoder("utf-8").decode(message.binaryBody);
        } else {
          body = message.body;
        }

        if (!body) {
          console.error("Message body is empty.");
          return;
        }

        try {
          const data: Recording[] = JSON.parse(body);
          callback(data);
        } catch (error) {
          console.error("JSON parse error:", error);
        }
      });
    },

    onStompError: (frame) => {
      console.error("STOMP Error:", frame.headers["message"]);
    },
  });

  stompClient.activate();
}

export function requestRecordings(): void {
  if (stompClient && stompClient.connected) {
    console.log("Requesting recordings...");
    stompClient.publish({
      destination: "/app/request-recordings",
      body: JSON.stringify({}),
    });
  } else {
    console.error("STOMP client is not connected.");
  }
}

export function submitRecordings(recording: Recording): void {
  if (stompClient && stompClient.connected) {
    console.log("Submitting recording:", recording);
    stompClient.publish({
      destination: "/app/update-recording",
      body: JSON.stringify(recording),
    });
  } else {
    console.error("STOMP client is not connected.");
  }
}

export function disconnect(): void {
  if (stompClient) {
    stompClient.deactivate();
    stompClient = null;
    console.log("STOMP client disconnected.");
  }
}
