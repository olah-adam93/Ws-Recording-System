package com.example.recording_app.service;

import com.example.recording_app.exception.RecordingNotFoundException;
import com.example.recording_app.model.Recording;
import com.example.recording_app.model.RecordingStatus;
import com.example.recording_app.repository.RecordingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecordingServiceTest {

    @Mock
    private RecordingRepository recordingRepository;

    @InjectMocks
    private RecordingService recordingService;

    @Test
    void getRecordings() {

        List<Recording> mockRecordings = Arrays.asList(
                new Recording("1", "EEG Recording - Patient A", 1200, RecordingStatus.SCHEDULED, "Propofol", "Auditory Stimulation", "Lorazepam"),
                new Recording("2", "EEG Recording - Patient B", 1800, RecordingStatus.RECORDED, "Midazolam", "Visual Stimulation", "Diazepam")
        );
        when(recordingRepository.findAll()).thenReturn(mockRecordings);

        List<Recording> result = recordingService.getRecordings();

        assertEquals(2, result.size(), "Expected two recordings in the result");
        assertEquals("EEG Recording - Patient A", result.get(0).getTitle());
        assertEquals("EEG Recording - Patient B", result.get(1).getTitle());
        verify(recordingRepository, times(1)).findAll();
    }

    @Test
    void updateRecording() {

        Recording existingRecording = new Recording("1", "EEG Recording - Patient A", 1200, RecordingStatus.SCHEDULED, "Propofol", "Auditory Stimulation", "Lorazepam");
        Recording updatedRecording = new Recording("1", "Updated EEG Recording", 1500, RecordingStatus.RECORDED, "Midazolam", "Visual Stimulation", "Diazepam");

        when(recordingRepository.findById("1")).thenReturn(Optional.of(existingRecording));
        when(recordingRepository.save(any(Recording.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Fix: Ensure save returns updated object

        recordingService.updateRecording(updatedRecording);

        assertEquals("Updated EEG Recording", existingRecording.getTitle(), "Title should be updated");
        assertEquals(1500, existingRecording.getDuration(), "Duration should be updated");
        assertEquals(RecordingStatus.RECORDED, existingRecording.getStatus(), "Status should be updated");
        assertEquals("Midazolam", existingRecording.getSedation(), "Sedation should be updated");
        assertEquals("Visual Stimulation", existingRecording.getActivation(), "Activation should be updated");
        assertEquals("Diazepam", existingRecording.getMedication(), "Medication should be updated");

        verify(recordingRepository, times(1)).findById("1");
        verify(recordingRepository, times(1)).save(any(Recording.class));
    }

    @Test
    void updateRecording_WhenRecordingNotFound() {
        Recording updatedRecording = new Recording("999", "Non-existent Recording", 0, RecordingStatus.SCHEDULED, "None", "None", "None");

        when(recordingRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(RecordingNotFoundException.class, () -> {
            recordingService.updateRecording(updatedRecording);
        });

        verify(recordingRepository, times(1)).findById("999");
        verify(recordingRepository, never()).save(any());
    }
}
