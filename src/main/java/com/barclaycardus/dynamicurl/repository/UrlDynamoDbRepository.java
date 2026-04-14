package com.barclaycardus.dynamicurl.repository;
import com.barclaycardus.dynamicurl.entity.UrlInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import java.util.*;
@Repository
public class UrlDynamoDbRepository {
    private final DynamoDbTable<UrlInformation> table;
    private final DynamoDbIndex<UrlInformation> contactCreatedOnIndex;
    public UrlDynamoDbRepository(DynamoDbEnhancedClient client,
                                 @Value("${aws.dynamodb.table-name}") String tableName) {
        this.table = client.table(tableName, TableSchema.fromBean(UrlInformation.class));
        this.contactCreatedOnIndex = table.index("contact-createdOn-index");
    }
    public UrlInformation findByUrlId(String urlId) {
        Key key = Key.builder()
                .partitionValue(urlId)
                .build();
        return table.getItem(r -> r.key(key));
    }
    public List<UrlInformation> findByContactAndJourneyCodeAndCreatedOn(
            String contact,
            String workflowCode,
            long startOfTodayEpochMillis,
            List<String> statuses) {
        Set<String> seenIds = new HashSet<>();
        List<UrlInformation> results = new ArrayList<>();
        // Part A: contact = X AND createdOn >= today
        QueryEnhancedRequest queryA = QueryEnhancedRequest.builder()
                .queryConditional(
                        QueryConditional.sortGreaterThanOrEqualTo(
                                Key.builder()
                                        .partitionValue(contact)
                                        .sortValue(startOfTodayEpochMillis)
                                        .build()
                        )
                )
                .filterExpression(
                        Expression.builder()
                                .expression("workflowCode = :wf")
                                .putExpressionValue(":wf", AttributeValue.builder().s(workflowCode).build())
                                .build()
                )
                .scanIndexForward(false)
                .build();
        for (Page<UrlInformation> page : contactCreatedOnIndex.query(queryA)) {
            for (UrlInformation item : page.items()) {
                if (seenIds.add(item.getUrlId())) {
                    results.add(item);
                }
            }
        }
        // Part B: contact = X AND status IN (ACTIVE, ADMIN_LOCKED)
        QueryEnhancedRequest queryB = QueryEnhancedRequest.builder()
                .queryConditional(
                        QueryConditional.keyEqualTo(
                                Key.builder()
                                        .partitionValue(contact)
                                        .build()
                        )
                )
                .filterExpression(
                        Expression.builder()
                                .expression("workflowCode = :wf AND #st IN (:s1, :s2)")
                                .putExpressionValue(":wf", AttributeValue.builder().s(workflowCode).build())
                                .putExpressionValue(":s1", AttributeValue.builder().s(statuses.get(0)).build())
                                .putExpressionValue(":s2", AttributeValue.builder().s(statuses.get(1)).build())
                                .expressionNames(Map.of("#st", "status"))
                                .build()
                )
                .scanIndexForward(false)
                .build();
        for (Page<UrlInformation> page : contactCreatedOnIndex.query(queryB)) {
            for (UrlInformation item : page.items()) {
                if (seenIds.add(item.getUrlId())) {
                    results.add(item);
                }
            }
        }
        results.sort(Comparator.comparingLong(UrlInformation::getCreatedOn).reversed());
        return results;
    }
    public UrlInformation save(UrlInformation entity) {
        table.putItem(entity);
        return entity;
    }
    public void updateState(String urlId, String status, Integer accessCount,
                            Integer failedCount, Long lockedUpto, Long updatedOn, String updatedBy) {
        UrlInformation update = new UrlInformation();
        update.setUrlId(urlId);
        update.setStatus(status);
        update.setUrlAccessCount(accessCount);
        update.setUrlAccessFailedCount(failedCount);
        update.setLockedUpto(lockedUpto);
        update.setUpdatedOn(updatedOn);
        update.setUpdatedBy(updatedBy);
        table.updateItem(update);
    }
}
