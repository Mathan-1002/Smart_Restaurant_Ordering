package com.smartrestaurant.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerSessionService {

    private final Map<Long, LocalDateTime> sessionMap = new HashMap<>();

    public void startSession(Long customerId) {
        sessionMap.put(customerId, LocalDateTime.now());
    }

    public LocalDateTime getEntryTime(Long customerId) {
        return sessionMap.get(customerId);
    }

    public void endSession(Long customerId) {
        sessionMap.remove(customerId);
    }
}
