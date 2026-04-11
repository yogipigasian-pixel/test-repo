package com.barclaycardus.dynamicurl.configuration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.http.apache.ProxyConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Set;
@Slf4j
@Configuration
public class DynamoDbConfig {
  @Value("${aws.region:${AWS_REGION:us-east-1}}")
  private String region;
  @Value("${aws.endpoint-url:${AWS_ENDPOINT_URL:}}")
  private String endpoint;
  @Bean
  public DynamoDbClient dynamoDBClient() throws URISyntaxException {
    log.info("Initializing DynamoDbClient region={}, endpoint={}", region, endpoint);
    String httpsProxy = System.getenv("HTTPS_PROXY");
    log.info("HTTPS_PROXY={}", httpsProxy);
    DynamoDbClientBuilder builder = DynamoDbClient.builder()
        .region(Region.of(region));
    if (StringUtils.isNotEmpty(httpsProxy)) {
      // Extract proxy host:port only (remove credentials from URL)
      // e.g. http://user:pass@proxy.host.com:8080 → http://proxy.host.com:8080
      String proxyEndpoint = httpsProxy.replaceAll("http://[^@]+@", "http://");
      ProxyConfiguration proxyConfig = ProxyConfiguration.builder()
          .endpoint(URI.create(proxyEndpoint))
          .nonProxyHosts(Set.of(
              "localhost",
              "127.0.0.1",
              ".amazonaws.com",
              "dynamodb.us-east-1.amazonaws.com"
          ))
          .build();
      log.info("Using proxy endpoint={}", proxyEndpoint);
      builder.httpClientBuilder(
          ApacheHttpClient.builder()
              .connectionTimeout(Duration.ofSeconds(10))
              .socketTimeout(Duration.ofSeconds(30))
              .proxyConfiguration(proxyConfig)
      );
    } else {
      log.info("No proxy configured, connecting directly");
      builder.httpClientBuilder(
          ApacheHttpClient.builder()
              .connectionTimeout(Duration.ofSeconds(10))
              .socketTimeout(Duration.ofSeconds(30))
      );
    }
    if (StringUtils.isNotEmpty(endpoint)) {
      URI endpointUri = new URI(endpoint);
      builder.endpointOverride(endpointUri);
      log.info("Using DynamoDB endpointOverride={}", endpointUri);
    } else {
      log.info("Using real AWS DynamoDB, region={}", region);
    }
    return builder.build();
  }
  @Bean
  public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
    return DynamoDbEnhancedClient.builder()
        .dynamoDbClient(dynamoDbClient)
        .build();
  }
}
