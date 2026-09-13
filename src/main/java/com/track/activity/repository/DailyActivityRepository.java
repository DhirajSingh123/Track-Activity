package com.track.activity.repository;

import com.track.activity.model.DailyActivity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DailyActivityRepository {

    private final DynamoDbTable<DailyActivity> activityTable;

    public DailyActivityRepository(
            DynamoDbEnhancedClient enhancedClient,
            @Value("${aws.dynamodb.activity-table-name}") String tableName) {

        this.activityTable = enhancedClient.table(
                tableName,
                TableSchema.fromBean(DailyActivity.class)
        );
    }

    public DailyActivity save(DailyActivity activity) {
        activityTable.putItem(activity);
        return activity;
    }

    public DailyActivity findById(String id) {

        Key key = Key.builder()
                .partitionValue(id)
                .build();

        return activityTable.getItem(key);
    }

    public List<DailyActivity> findAll() {

        List<DailyActivity> activities = new ArrayList<>();

        activityTable.scan()
                .items()
                .forEach(activities::add);

        return activities;
    }
}