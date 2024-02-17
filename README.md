## Global TODOs
1. @Valid annotations are ignored, as they aren't in the web layer
2. Docker build fails due to corrupted wrapper being passed to container on build
3. data.sql isn't loaded
4. ```yaml
    springdoc:
        swagger-ui:
            csrf:
                enabled: true 
    ```
   doesn't do anything by itself, even providing a csrf endpoint / cookieRepository configuration
   fails as csrf filter does some incorrect encoding/decoding for tokens 