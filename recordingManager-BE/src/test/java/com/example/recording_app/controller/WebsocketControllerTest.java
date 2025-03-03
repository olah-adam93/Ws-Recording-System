package com.example.recording_app.controller;

import com.example.recording_app.model.Recording;
import com.example.recording_app.model.RecordingStatus;
import com.example.recording_app.service.RecordingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebsocketControllerTest {

    @Mock
    private RecordingService recordingService;

    @InjectMocks
    private WebsocketController websocketController;

    @Test
    void testSendRecordings() {
        // Arrange
        List<Recording> mockRecordings = List.of(
                new Recording("1", "EEG Recording - Patient A", 1200, RecordingStatus.SCHEDULED, "Propofol", "Auditory Stimulation", "Lorazepam"),
                new Recording("2", "EEG Recording - Patient B", 1800, RecordingStatus.RECORDED, "Midazolam", "Visual Stimulation", "Diazepam")
        );
        when(recordingService.getRecordings()).thenReturn(mockRecordings);

        List<Recording> result = websocketController.sendRecordings();

        assertEquals(2, result.size());
        assertEquals("EEG Recording - Patient A", result.get(0).getTitle());
        assertEquals("EEG Recording - Patient B", result.get(1).getTitle());
        verify(recordingService, times(1)).getRecordings();
    }

    @Test
    void testUpdateRecording() {

        Recording updatedRecording = new Recording("1", "EEG Recording - Patient A", 1200, RecordingStatus.RECORDED, "Propofol", "Auditory Stimulation", "Lorazepam");

        List<Recording> mockRecordings = new ArrayList<>(List.of(
                new Recording("1", "EEG Recording - Patient A", 1200, RecordingStatus.SCHEDULED, "Propofol", "Auditory Stimulation", "Lorazepam"),
                new Recording("2", "EEG Recording - Patient B", 1800, RecordingStatus.RECORDED, "Midazolam", "Visual Stimulation", "Diazepam")
        ));

        when(recordingService.getRecordings()).thenReturn(mockRecordings);

        doAnswer(invocation -> {
            Recording arg = invocation.getArgument(0);
            mockRecordings.stream()
                    .filter(rec -> rec.getId().equals(arg.getId()))
                    .forEach(rec -> rec.setStatus(arg.getStatus()));
            return null;
        }).when(recordingService).updateRecording(any(Recording.class));

        websocketController.updateRecording(updatedRecording);

        assertEquals(2, mockRecordings.size());
        assertEquals(RecordingStatus.RECORDED, mockRecordings.get(0).getStatus());
        verify(recordingService, times(1)).updateRecording(updatedRecording);
        verify(recordingService, times(1)).getRecordings();
    }
}
