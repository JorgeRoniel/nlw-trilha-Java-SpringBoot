package com.shrkltda.planner.links;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer>{
    List<Link> findByTripId(int tripId);
}
