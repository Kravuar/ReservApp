## Global TODOs

1. <span style="color:blue">(POLISHING)</span> Fixed domain validation, but need to remove same validation bpp configuration from all services
2. <span style="color:blue">(POLISHING)</span> Docker build fails due to corrupted wrapper being passed to container on build
3. <span style="color:green">(MINOR)</span> Create guide for configuring okta.dev
4. <span style="color:green">(MINOR)</span> Maybe include some criteria like stuff in domain in RetrievalPorts
5. <span style="color:green">(MINOR)</span> Make jpa hibernate adapters return references by id (not fetching) or something
6. <span style="color:green">(MINOR)</span> Kafka deserialization automatic conversion (for now it's the `commons.integration-dto` stuff):
   - Producer sends {a, b, c}: ProducerClass,
   - Consumer receives {a, c}: ConsumerClass (for example), automatically converts json
     and delegates to correct `@KafkaHandler` (by parameter Type)

   No dependencies, no unnecessary fields, cool in general
7. <span style="color:green">(MINOR)</span> Use Redisson for multi-instance lock consistency
8. <span style="color:red">(CRITICAL)</span> Cache adapters will fail synchronization if the lock is used longer than set expiration time
   as the still active lock will be deleted from cache, and new one will be created for same key on the upcoming request
9. <span style="color:blue">(POLISHING)</span> Remove duplication in application properties, exception handling and some other stuff
10. <span style="color:green">(MINOR)</span> Use error codes instead of messages in exceptions
11. <span style="color:orange">(MEDIUM)</span> Define schema.sql with constraints, indexes and other staff
12. <span style="color:green">(MINOR)</span> Eureka and other spring cloud staff
