package com.barclaycardus.dynamicurl.configuration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
@Slf4j
@Configuration
public class DynamoDbConfig {
  @Value("${aws.region:${AWS_REGION:us-east-1}}")
  private String region;
  @Value("${aws.endpoint-url:${AWS_ENDPOINT_URL:}}")
  private String endpoint;
  @Bean
  public DynamoDbClient dynamoDBClient() throws URISyntaxException {
    log.info("Initializing DynamoDbClient with region={}, endpoint={}", region, endpoint);
    Region dynamoDBRegion = Region.of(region);
    DynamoDbClientBuilder dynamoDbClientBuilder = DynamoDbClient.builder()
        .httpClientBuilder(
            ApacheHttpClient.builder()
                .connectionTimeout(Duration.ofSeconds(10))
                .socketTimeout(Duration.ofSeconds(30))
        )
        .region(dynamoDBRegion);
    if (StringUtils.isNotEmpty(endpoint)) {
      URI endpointUri = new URI(endpoint);
      dynamoDbClientBuilder.endpointOverride(endpointUri);
      log.info("Using DynamoDB endpointOverride={}", endpointUri);
    } else {
      log.info("Using real AWS DynamoDB, region={}", region);
    }
    return dynamoDbClientBuilder.build();
  }
  @Bean
  public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
    return DynamoDbEnhancedClient.builder()
        .dynamoDbClient(dynamoDbClient)
        .build();
  }
}
