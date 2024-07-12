package com.shrkltda.planner.participants;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shrkltda.planner.trip.Trip;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository repository;
    
    public void registerParticipantstoEvent(List<String> participantsToInvite, Trip trip){
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        this.repository.saveAll(participants);
    }

    public ParticipantResponseDTO registerParticipantToEvent(String email, Trip trip){
        Participant newParticipant = new Participant(email, trip);
        this.repository.save(newParticipant);

        return  new ParticipantResponseDTO(newParticipant.getId());
    }

    public void triggerConfirmedEmailToParticipants(int id){}

    public void triggerConfirmedEmailToParticipant(String email){}

    public List<ParticipantsData> getAllParticipantsFromEvent(int tripId){
        return this.repository.findByTripId(tripId).stream().map(participants -> new ParticipantsData(participants.getId(), participants.getNome(), participants.getEmail(), participants.getIsConfirmed())).toList();
    }

}
