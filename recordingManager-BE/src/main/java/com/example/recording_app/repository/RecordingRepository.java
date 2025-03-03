package com.example.recording_app.repository;

import com.example.recording_app.model.Recording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordingRepository extends JpaRepository<Recording, String> {
}
