package com.track.activity.service;

import com.track.activity.dto.CreateDailyActivityRequest;
import com.track.activity.model.DailyActivity;
import com.track.activity.model.Plan;
import com.track.activity.repository.DailyActivityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class DailyActivityService {

    private final DailyActivityRepository dailyActivityRepository;
    private final PlanService planService;

    public DailyActivityService(
            DailyActivityRepository dailyActivityRepository,
            PlanService planService) {

        this.dailyActivityRepository = dailyActivityRepository;
        this.planService = planService;
    }

    public DailyActivity addActivity(
            String planId,
            CreateDailyActivityRequest request) {

        Plan plan = planService.getPlan(planId);

        LocalDate today = LocalDate.now();

        LocalDate startDate =
                LocalDate.parse(plan.getStartDate());

        LocalDate targetDate =
                LocalDate.parse(plan.getTargetDate());

        if (today.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "Plan has not started yet"
            );
        }

        if (today.isAfter(targetDate)) {
            throw new IllegalArgumentException(
                    "Plan target date has already passed"
            );
        }

        DailyActivity activity = new DailyActivity();

        activity.setId(UUID.randomUUID().toString());

        activity.setPlanId(planId);

        activity.setActivityDate(today.toString());

        activity.setTargetMinutes(
                plan.getTargetMinutesPerDay()
        );

        activity.setActualMinutes(
                request.getActualMinutes()
        );

        activity.setNotes(
                request.getNotes()
        );

        boolean completed =
                request.getActualMinutes()
                        >= plan.getTargetMinutesPerDay();

        activity.setCompleted(completed);

        double dailyPercentage =
                ((double) request.getActualMinutes()
                        / plan.getTargetMinutesPerDay())
                        * 100;

        // optional: don't allow a day to exceed 100%
        dailyPercentage = Math.min(dailyPercentage, 100);

        dailyPercentage =
                Math.round(dailyPercentage * 100.0) / 100.0;

        activity.setAchievementPercentage(
                dailyPercentage
        );

        dailyActivityRepository.save(activity);

        recalculatePlanProgress(plan);

        return activity;
    }

    private void recalculatePlanProgress(Plan plan) {

        List<DailyActivity> activities =
                dailyActivityRepository.findAll()
                        .stream()
                        .filter(activity ->
                                plan.getId().equals(
                                        activity.getPlanId()
                                ))
                        .toList();

        int totalActualMinutes =
                activities.stream()
                        .mapToInt(
                                DailyActivity::getActualMinutes
                        )
                        .sum();

        int expectedMinutes =
                plan.getTotalDays()
                        * plan.getTargetMinutesPerDay();

        double achievementPercentage = 0;

        if (expectedMinutes > 0) {

            achievementPercentage =
                    ((double) totalActualMinutes
                            / expectedMinutes)
                            * 100;

            achievementPercentage =
                    Math.min(
                            achievementPercentage,
                            100
                    );

            achievementPercentage =
                    Math.round(
                            achievementPercentage * 100.0
                    ) / 100.0;
        }

        long completedDays =
                activities.stream()
                        .filter(
                                DailyActivity::isCompleted
                        )
                        .count();

        planService.updateProgress(
                plan,
                (int) completedDays,
                totalActualMinutes,
                achievementPercentage
        );
    }
}