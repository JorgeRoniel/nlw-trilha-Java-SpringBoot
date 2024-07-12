package com.shrkltda.planner.activities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shrkltda.planner.trip.Trip;

@Service
public class ActivityService {
    
    @Autowired
    private ActivityRepository repository;

    public ActivityResponse saveActivity(ActivityDTO data, Trip trip){
        Activity newActivity = new Activity(data, trip);

        this.repository.save(newActivity);

        return new ActivityResponse(newActivity.getId());
    }

    public List<ActivityData> listAllactivitiesFromId(int id){
        return repository.findByTripId(id).stream().map(activity -> new ActivityData(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();
    }
}
