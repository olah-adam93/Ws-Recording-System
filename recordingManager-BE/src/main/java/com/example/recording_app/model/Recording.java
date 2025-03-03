package com.example.recording_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recording {

    @Id
    private String id;
    private String title;
    private int duration;
    @Enumerated(EnumType.STRING)
    private RecordingStatus status;

    private String sedation;
    private String activation;
    private String medication;
}
