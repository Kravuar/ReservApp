## Global TODOs
1. Fixed domain validation, but need to remove same validation bpp configuration from all services
2. Docker build fails due to corrupted wrapper being passed to container on build
3. ```yaml
    springdoc:
        swagger-ui:
            csrf:
                enabled: true 
    ```
   doesn't do anything by itself, even providing a csrf endpoint / cookieRepository configuration
   fails as csrf filter does some incorrect encoding/decoding for tokens
4. Create guide for configuring okta.dev
5. Maybe include some criteria like stuff in domain in RetrievalPorts
6. Make jpa hibernate adapters return references by id (not fetching) or something
7. Add separate profile and docker-compose for each microservice to launch them individually
8. Kafka deserialization automatic conversion (for now it's the `commons.integration-dto` stuff):
   - Producer sends {a, b, c}: ProducerClass,
   - Consumer receives {a, c}: ConsumerClass (for example), automatically converts json 
     and delegates to correct `@KafkaHandler` (by parameter Type)
   
   No dependencies, no unnecessary fields, cool in general
9. Move all consistency checks from persistence ports to domain facades
10. Use Redisson for multi-instance lock consistency
11. Remove duplication in application properties?
12. Use error codes instead of messages in exceptions
13. Do I need a lock and synchronization at all for simple actions like update description, activeness...?