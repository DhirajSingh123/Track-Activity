package com.track.activity.service;

import com.track.activity.model.Plan;
import com.track.activity.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan createPlan(Plan plan) {
        

        LocalDate startDate =
                LocalDate.parse(plan.getStartDate());

        LocalDate targetDate =
                LocalDate.parse(plan.getTargetDate());

        if (targetDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "Target date cannot be before start date"
            );
        }

        long totalDays =
                ChronoUnit.DAYS.between(
                        startDate,
                        targetDate
                ) + 1;

        plan.setId(generateUniquePlanId(plan.getCategory(),plan.getSubCategory()));

        plan.setTotalDays((int) totalDays);

        plan.setCompletedDays(0);

        plan.setAchievementPercentage(0.0);

        plan.setStatus("ACTIVE");

        return planRepository.save(plan);
    }

    public Plan getPlan(String planId) {

        Plan plan = planRepository.findById(planId);

        if (plan == null) {
            throw new RuntimeException(
                    "Plan not found: " + planId
            );
        }

        return plan;
    }

    public void updateProgress(
            Plan plan,
            int completedDays,
            int totalActualMinutes,
            double achievementPercentage) {

        plan.setCompletedDays(completedDays);

        plan.setTotalActualMinutes(
                totalActualMinutes
        );

        plan.setAchievementPercentage(
                achievementPercentage
        );

        if (achievementPercentage >= 100) {
            plan.setStatus("COMPLETED");
        } else {
            plan.setStatus("ACTIVE");
        }

        planRepository.save(plan);
    }

    private String generateUniquePlanId(
            String userId,
            String subCategory) {

        String planId;
            int randomNumber = ThreadLocalRandom.current()
                    .nextInt(1000, 10000);

            planId = userId.toUpperCase()
                    + "-"
                    + subCategory.toUpperCase()
                    + "-"
                    + randomNumber;
        return planId;
    }
}