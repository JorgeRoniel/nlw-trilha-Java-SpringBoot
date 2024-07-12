package com.shrkltda.planner.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shrkltda.planner.activities.ActivityDTO;
import com.shrkltda.planner.activities.ActivityData;
import com.shrkltda.planner.activities.ActivityResponse;
import com.shrkltda.planner.activities.ActivityService;
import com.shrkltda.planner.links.LinkDTO;
import com.shrkltda.planner.links.LinkResponse;
import com.shrkltda.planner.links.LinkService;
import com.shrkltda.planner.participants.ParticipantDTO;
import com.shrkltda.planner.participants.ParticipantResponseDTO;
import com.shrkltda.planner.participants.ParticipantService;
import com.shrkltda.planner.participants.ParticipantsData;
import com.shrkltda.planner.trip.RegisterTripDTO;
import com.shrkltda.planner.trip.Trip;
import com.shrkltda.planner.trip.TripRepository;
import com.shrkltda.planner.trip.TripResponseDTO;

@RestController
@RequestMapping("/trips")
@SuppressWarnings("rawtypes")
public class TripController {
    @Autowired
    private TripRepository repository;

    @Autowired
    private ParticipantService participants;

    @Autowired
    private ActivityService activity;

    @Autowired
    private LinkService link;

    @PostMapping
    public ResponseEntity<TripResponseDTO> create(@RequestBody RegisterTripDTO payload){
        Trip trip = new Trip(payload);
        
        repository.save(trip);
        participants.registerParticipantstoEvent(payload.emails_to_invite(), trip);

        return ResponseEntity.ok(new TripResponseDTO(trip.getId()));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity getTrip(@PathVariable("id") int id){
        Optional<Trip> trip = repository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable("id") int id, @RequestBody RegisterTripDTO payload){
        Optional<Trip> trip = repository.findById(id);

        if(trip.isPresent()){
            Trip newTrip = trip.get();
            newTrip.setDestination(payload.destination());
            newTrip.setStartAt(LocalDateTime.parse(payload.starts_at()));
            newTrip.setEndAt(LocalDateTime.parse(payload.ends_at()));

            repository.save(newTrip);

            return ResponseEntity.ok(newTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}/confirm")
    public ResponseEntity<Trip> confirmedTrip(@PathVariable("id") int id){
        Optional<Trip> trip = repository.findById(id);

        if(trip.isPresent()){
            Trip newTrip = trip.get();
            newTrip.setIsConfirmed(true);
            participants.triggerConfirmedEmailToParticipants(id);

            repository.save(newTrip);

            return ResponseEntity.ok(newTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantResponseDTO> inviteParticipant(@PathVariable("id") int id, @RequestBody ParticipantDTO payload){
        Optional<Trip> trip = this.repository.findById(id);

        if(trip.isPresent()){
            Trip newTrip = trip.get();

            ParticipantResponseDTO participantResponse = this.participants.registerParticipantToEvent(payload.email(), newTrip);

            if(newTrip.getIsConfirmed()) this.participants.triggerConfirmedEmailToParticipant(payload.email());

            return ResponseEntity.ok(participantResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantsData>> listAllParticipants(@PathVariable("id") int id){
        List<ParticipantsData> participantsList = this.participants.getAllParticipantsFromEvent(id);

        return ResponseEntity.ok(participantsList);
    }

    @PostMapping("/{id}/activity")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable("id") int id, @RequestBody ActivityDTO payload){
        Optional<Trip> trip = repository.findById(id);

        if(trip.isPresent()){
            Trip newTrip = trip.get();

            ActivityResponse activityResponse = this.activity.saveActivity(payload, newTrip);

            return ResponseEntity.ok(activityResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/activity")
    public ResponseEntity<List<ActivityData>> listAllActivities(@PathVariable("id") int id){
        List<ActivityData> activityList = this.activity.listAllactivitiesFromId(id);

        return ResponseEntity.ok(activityList);
    }

    @PostMapping("/{id}/addlink")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable("id") int id, @RequestBody LinkDTO payload){
        Optional<Trip> trip = repository.findById(id);

        if(trip.isPresent()){
            Trip newTrip = trip.get();

            LinkResponse linkResponse = this.link.saveLink(payload, newTrip);

            return ResponseEntity.ok(linkResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkDTO>> listAllLinks(@PathVariable("id") int id){
        List<LinkDTO> linksList = this.link.listAllLinksFromId(id);
        return ResponseEntity.ok(linksList);
    }

}
