package com.track.activity.controller;

import com.track.activity.model.Plan;
import com.track.activity.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(
            PlanService planService) {

        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<Plan> createPlan(
            @RequestBody Plan plan) {
        return ResponseEntity.ok(
                planService.createPlan(plan)
        );
    }

    @GetMapping("/{planId}")
    public ResponseEntity<Plan> getPlan(
            @PathVariable String planId) {

        return ResponseEntity.ok(
                planService.getPlan(planId)
        );
    }
}