package com.shrkltda.planner.trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String destination;

    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "ends_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;
    
    @Column(name = "onwer_name", nullable = false)
    private String onwerName;

    @Column(name = "onwer_email", nullable = false)
    private String onwerEmail;

    public Trip(RegisterTripDTO data){
        this.destination = data.destination();
        this.isConfirmed = false;
        this.onwerEmail = data.onwer_email();
        this.onwerName = data.onwer_name();
        this.startAt = LocalDateTime.parse(data.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
        this.endAt = LocalDateTime.parse(data.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
    }
}
