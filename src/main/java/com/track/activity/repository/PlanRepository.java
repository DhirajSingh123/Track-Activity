package com.track.activity.repository;

import com.track.activity.model.Plan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;

@Repository
public class PlanRepository {

    private final DynamoDbTable<Plan> planTable;

    public PlanRepository(
            DynamoDbEnhancedClient enhancedClient,
            @Value("${aws.dynamodb.plan-table-name}") String tableName) {

        this.planTable = enhancedClient.table(
                tableName,
                TableSchema.fromBean(Plan.class)
        );
    }

    public Plan save(Plan plan) {
        planTable.putItem(plan);
        return plan;
    }

    public Plan findById(String id) {

        Key key = Key.builder()
                .partitionValue(id)
                .build();

        return planTable.getItem(key);
    }
}