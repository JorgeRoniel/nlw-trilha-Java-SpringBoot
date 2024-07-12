package com.shrkltda.planner.trip;

import java.util.List;

public record RegisterTripDTO(String destination, String starts_at, String ends_at, List<String> emails_to_invite, String onwer_email, String onwer_name) {
    
}
