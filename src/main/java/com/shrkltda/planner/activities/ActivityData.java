package com.shrkltda.planner.activities;

import java.time.LocalDateTime;

public record ActivityData(int id, String title, LocalDateTime occurs_at) {
    
}
