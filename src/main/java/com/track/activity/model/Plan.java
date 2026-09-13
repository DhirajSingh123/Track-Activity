package com.track.activity.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Plan {

    private String id;
    private String title;
    private String description;
    private String category;
    private String subCategory;
    private String status;

    private int targetMinutesPerDay;

    private String startDate;
    private String targetDate;

    private int totalDays;
    private int completedDays;
    private double achievementPercentage;

    private int totalActualMinutes;

    public Plan() {
    }

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTargetMinutesPerDay() {
        return targetMinutesPerDay;
    }

    public void setTargetMinutesPerDay(int targetMinutesPerDay) {
        this.targetMinutesPerDay = targetMinutesPerDay;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getCompletedDays() {
        return completedDays;
    }

    public void setCompletedDays(int completedDays) {
        this.completedDays = completedDays;
    }

    public double getAchievementPercentage() {
        return achievementPercentage;
    }

    public void setAchievementPercentage(double achievementPercentage) {
        this.achievementPercentage = achievementPercentage;
    }

    public int getTotalActualMinutes() {
        return totalActualMinutes;
    }

    public void setTotalActualMinutes(int totalActualMinutes) {
        this.totalActualMinutes = totalActualMinutes;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
