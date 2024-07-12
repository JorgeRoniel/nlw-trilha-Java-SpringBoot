package com.shrkltda.planner.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shrkltda.planner.participants.Participant;
import com.shrkltda.planner.participants.ParticipantDTO;
import com.shrkltda.planner.participants.ParticipantRepository;

@RestController
@RequestMapping("/participant")
public class ParticipantController {
    @Autowired
    private ParticipantRepository repository;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirm(@PathVariable("id") int id, @RequestBody ParticipantDTO payload){
        Optional<Participant> p = this.repository.findById(id);

        if(p.isPresent()){
            Participant newParticipant = p.get();
            newParticipant.setIsConfirmed(true);
            newParticipant.setNome(payload.nome());

            this.repository.save(newParticipant);

            return ResponseEntity.ok(newParticipant);
        }

        return ResponseEntity.notFound().build();
    }
}
