package com.example.recording_app.exception;

public class RecordingNotFoundException extends RuntimeException {
    public RecordingNotFoundException(String message) {
        super(message);
    }
}
