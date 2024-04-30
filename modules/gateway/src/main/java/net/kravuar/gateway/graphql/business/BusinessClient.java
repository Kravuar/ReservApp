package net.kravuar.gateway.graphql.business;

import feign.Headers;
import feign.Param;
import net.kravuar.pageable.Page;
import net.kravuar.staff.dto.BusinessDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "business",
        url = "http://business:8080/business/api-v1/"
)
@Headers(HttpHeaders.AUTHORIZATION + ": {requester}")
interface BusinessClient {

    @GetMapping("/retrieval/active/{page}/{pageSize}")
    Page<BusinessDTO> activeBusinesses(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize);

    @GetMapping("/retrieval/my/{page}/{pageSize}")
    Page<BusinessDTO> myBusinesses(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @Param("requester") String requester);
}
