package com.example.recording_app.controller;

import com.example.recording_app.model.Recording;
import com.example.recording_app.service.RecordingService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebsocketController {

    private final RecordingService recordingService;

    public WebsocketController(RecordingService recordingService) {
        this.recordingService = recordingService;
    }

    @MessageMapping("/request-recordings")
    @SendTo("/topic/recordings")
    public List<Recording> sendRecordings() {
        return recordingService.getRecordings();
    }

    @MessageMapping("/update-recording")
    @SendTo("/topic/recordings")
    public List<Recording> updateRecording(@Payload Recording updatedRecording) {
        recordingService.updateRecording(updatedRecording);
        return recordingService.getRecordings();
    }
}
