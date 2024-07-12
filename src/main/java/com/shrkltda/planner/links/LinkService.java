package com.shrkltda.planner.links;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shrkltda.planner.trip.Trip;

@Service
public class LinkService {

    @Autowired
    private LinkRepository repository;
    
    public LinkResponse saveLink(LinkDTO data, Trip trip){
        Link newLink = new Link(data.title(), data.url(), trip);
        this.repository.save(newLink);

        return new LinkResponse(newLink.getId());
    }

    public List<LinkDTO> listAllLinksFromId(int id){
        return this.repository.findByTripId(id).stream().map(link -> new LinkDTO(link.getTitle(), link.getUrl())).toList();
    }
}
