package com.track.activity.repository;

import com.track.activity.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class UserRepository {

    private final DynamoDbTable<User> userTable;

    public UserRepository(
            DynamoDbEnhancedClient enhancedClient,
            @Value("${aws.dynamodb.user-table-name}") String tableName) {

        this.userTable = enhancedClient.table(
                tableName,
                TableSchema.fromBean(User.class)
        );
    }

    public User save(User user) {
        userTable.putItem(user);
        return user;
    }

    public User findByPhoneNo(String phoneNo) {

        return userTable.scan()
                .items()
                .stream()
                .filter(user -> phoneNo.equals(user.getPhoneNo()))
                .findFirst()
                .orElse(null);
    }

    public User findByEmailId(String emailId) {

        return userTable.scan()
                .items()
                .stream()
                .filter(user -> emailId.equalsIgnoreCase(user.getEmailId()))
                .findFirst()
                .orElse(null);
    }
}