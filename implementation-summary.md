# Implementation Summary: Cloud Application Modernization

This document provides a comprehensive overview of the five phases implemented to modernize the `cloud-store` backend, transitioning it from a monolithic architecture to a scalable, distributed system.

---

## Phase 1: Core Architecture Documentation
Finalized the high-level design encompassing the Next.js frontend, Spring Boot backend, PostgreSQL for metadata, and MinIO for object storage.

---

## Phase 2: Caching Layer (Redis)
**Goal:** Improve performance by offloading frequent database queries.

### 1. Configuration (`application.properties`)
```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.type=redis
spring.cache.redis.time-to-live=600000
```

### 2. Infrastructure (`CacheConfig.java`)
Enabled Spring Caching and configured JSON serialization for Redis to store human-readable data.
```java
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
    }
}
```

### 3. Service Integration (`UserService.java`)
```java
@Cacheable(value = "users", key = "#id")
public UserResponse get(UUID id) { ... }

@CacheEvict(value = "allUsers", allEntries = true)
public UserResponse create(CreateUserRequest request) { ... }
```

---

## Phase 3: Async Processing (RabbitMQ)
**Goal:** Decouple resource-intensive tasks from the main request-response cycle.

### 1. Configuration (`RabbitMQConfig.java`)
Defined a Topic Exchange and Queues for Audit Logging and Image Thumbnail generation.
```java
public static final String AUDIT_QUEUE = "audit.queue";
public static final String THUMBNAIL_QUEUE = "thumbnail.queue";
public static final String CLOUDSTORE_EXCHANGE = "cloudstore.exchange";

@Bean
public Binding auditBinding(Queue auditQueue, TopicExchange exchange) {
    return BindingBuilder.bind(auditQueue).to(exchange).with("audit.*");
}
```

### 2. Producer (`AuditService.java`)
Instead of saving to the DB directly, the service now publishes a message.
```java
public void log(UUID userId, String action, UUID nodeId, String metadataJson) {
    AuditEventMessage message = new AuditEventMessage(userId, action, nodeId, metadataJson, OffsetDateTime.now());
    rabbitTemplate.convertAndSend(CLOUDSTORE_EXCHANGE, "audit.event", message);
}
```

### 3. Consumer (`AuditEventConsumer.java`)
Background worker that processes and persists the logs.
```java
@RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
public void consumeAuditEvent(AuditEventMessage message) {
    auditEventRepository.save(mapToEntity(message));
}
```

---

## Phase 4: Advanced Search (Elasticsearch)
**Goal:** Replace slow SQL `LIKE` queries with high-performance full-text search.

### 1. Document Mapping (`StorageNodeDocument.java`)
```java
@Document(indexName = "storage_nodes")
public class StorageNodeDocument {
    @Id private String id;
    @Field(type = FieldType.Text, analyzer = "standard") private String name;
    private UUID ownerId;
    private boolean trashed;
}
```

### 2. Search Logic (`StorageNodeService.java`)
The search method now queries Elasticsearch instead of PostgreSQL.
```java
public List<StorageNode> search(UUID ownerId, String query) {
    List<StorageNodeDocument> docs = searchRepository.findByNameAndOwnerIdAndTrashedFalse(query.trim(), ownerId);
    List<UUID> ids = docs.stream().map(d -> UUID.fromString(d.getId())).toList();
    return storageNodeRepository.findAllById(ids);
}
```

### 3. Automatic Indexing
Every node mutation (`create`, `rename`, `trash`) triggers an update to the Elasticsearch index to keep data in sync.

---

## Phase 5: External Identity Provider (OAuth2)
**Goal:** Centralize security and enable enterprise features like MFA.

### 1. Resource Server Config (`SecurityConfig.java`)
Configured the app to validate JWTs from an external issuer (e.g., Keycloak).
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .build();
}
```

### 2. Unified User Identity (`CurrentUserService.java`)
Refactored to extract the user's UUID from the standard `sub` claim of the OAuth2 token.
```java
public Users requireCurrentUser() {
    String subject = SecurityContextHolder.getContext().getAuthentication().getName();
    UUID userId = UUID.fromString(subject);
    return userRepository.findById(userId).orElseThrow(...);
}
```

---

## Implementation Summary Table
| Feature | Component | Snippet Focus |
| :--- | :--- | :--- |
| **Caching** | Redis | `@Cacheable` on reads, `@CacheEvict` on writes. |
| **Messaging** | RabbitMQ | `RabbitTemplate` for async logging and file events. |
| **Search** | Elasticsearch | `ElasticsearchRepository` for name-based file search. |
| **Security** | OAuth2/IdP | `oauth2ResourceServer` for standardized JWT validation. |
