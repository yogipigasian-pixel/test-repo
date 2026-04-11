# Dummy Python file 3

# import software.amazon.awssdk.http.apache.ApacheHttpClient;
# import java.time.Duration;
#
# @Bean
# public DynamoDbClient dynamoDBClient() throws URISyntaxException {
#     Region dynamoDBRegion = Region.of(region);
#     DynamoDbClientBuilder dynamoDbClientBuilder = DynamoDbClient.builder()
#         .httpClientBuilder(ApacheHttpClient.builder()
#             .connectionTimeout(Duration.ofSeconds(10))
#             .socketTimeout(Duration.ofSeconds(30)))
#         .region(dynamoDBRegion);
#     if (StringUtils.isNotEmpty(endpoint)) {
#         dynamoDbClientBuilder.endpointOverride(new URI(endpoint));
#     }
#     return dynamoDbClientBuilder.build();
# }
