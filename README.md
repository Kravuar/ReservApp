## Global TODOs
1. @Valid annotations are ignored, as they aren't in the web layer
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
8. Failed to remove spring from domain for now (@Transactional), though it's possible