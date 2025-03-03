package com.example.recording_app.service;

import com.example.recording_app.exception.RecordingNotFoundException;
import com.example.recording_app.model.Recording;
import com.example.recording_app.repository.RecordingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordingService {

    private final RecordingRepository recordingRepository;

    RecordingService(RecordingRepository recordingRepository) {
        this.recordingRepository = recordingRepository;
    }

    public List<Recording> getRecordings() {
        return recordingRepository.findAll();
    }

    public void updateRecording(Recording updatedRecording) {

        Recording recording = recordingRepository.findById(updatedRecording.getId()).orElseThrow(() -> new RecordingNotFoundException("Recording with ID: " + updatedRecording.getId() + " not found."));

        recording.setTitle(updatedRecording.getTitle());
        recording.setStatus(updatedRecording.getStatus());
        recording.setDuration(updatedRecording.getDuration());
        recording.setSedation(updatedRecording.getSedation());
        recording.setActivation(updatedRecording.getActivation());
        recording.setMedication(updatedRecording.getMedication());

        recordingRepository.save(recording);
    }
}
