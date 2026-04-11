# Dummy Python file 2

# @Bean
# public DynamoDbClient dynamoDBClient() throws URISyntaxException {
#     System.out.println("=== DYNAMO DEBUG ===");
#     System.out.println("region=" + region);
#     System.out.println("endpoint=" + endpoint);
#     System.out.println("AWS_ACCESS_KEY_ID=" + System.getenv("AWS_ACCESS_KEY_ID"));
#     System.out.println("HTTPS_PROXY=" + System.getenv("HTTPS_PROXY"));
#     System.out.println("NO_PROXY=" + System.getenv("NO_PROXY"));
#     System.out.println("===================");
#     Region dynamoDBRegion = Region.of(region);
#     SdkHttpClient crtHttpClient = AwsCrtHttpClient.builder().build();
#     DynamoDbClientBuilder dynamoDbClientBuilder = DynamoDbClient.builder()
#         .httpClient(crtHttpClient)
#         .region(dynamoDBRegion);
#     if (StringUtils.isNotEmpty(endpoint)) {
#         URI endpointUri = new URI(endpoint);
#         dynamoDbClientBuilder.endpointOverride(endpointUri);
#     }
#     return dynamoDbClientBuilder.build();
# }
