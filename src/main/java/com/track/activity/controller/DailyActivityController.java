package com.track.activity.controller;

import com.track.activity.dto.CreateDailyActivityRequest;
import com.track.activity.model.DailyActivity;
import com.track.activity.service.DailyActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
public class DailyActivityController {

    private final DailyActivityService dailyActivityService;

    public DailyActivityController(
            DailyActivityService dailyActivityService) {

        this.dailyActivityService =
                dailyActivityService;
    }

    @PostMapping("/{planId}/activities")
    public ResponseEntity<DailyActivity> addActivity(
            @PathVariable String planId,
            @RequestBody CreateDailyActivityRequest request) {

        return ResponseEntity.ok(
                dailyActivityService.addActivity(
                        planId,
                        request
                )
        );
    }
}