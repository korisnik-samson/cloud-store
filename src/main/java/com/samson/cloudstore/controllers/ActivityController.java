package com.samson.cloudstore.controllers;

import com.samson.cloudstore.dto.ActivityResponse;
import com.samson.cloudstore.models.AuditEvent;
import com.samson.cloudstore.services.AuditService;
import com.samson.cloudstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final AuditService auditService;
    private final UserService userService;

    @GetMapping("/api/v1/activity")
    public ResponseEntity<List<ActivityResponse>> getActivity() {
        UUID userId = userService.getAuthenticatedUserId();
        List<AuditEvent> events = auditService.latestForUser(userId);

        // TODO: Fix this
        List<ActivityResponse> response = events.stream().map(ev -> new ActivityResponse(
                ev.getId(),
                ev.getAction(),
                ev.getNodeId(),
                ev.getMetadata(),
                ev.getCreatedAt()
        )).toList();

        return ResponseEntity.ok(response);
    }

}
