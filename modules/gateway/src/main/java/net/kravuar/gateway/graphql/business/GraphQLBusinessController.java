package net.kravuar.gateway.graphql.business;

import lombok.RequiredArgsConstructor;
import net.kravuar.business.dto.BusinessCreationDTO;
import net.kravuar.business.dto.BusinessDTO;
import net.kravuar.business.dto.BusinessDetailsDTO;
import net.kravuar.pageable.Page;
import net.kravuar.services.dto.ServiceDTO;
import net.kravuar.staff.dto.StaffDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static net.kravuar.gateway.graphql.BatchHelper.mapEntitiesToRelatedData;

@Controller
@RequiredArgsConstructor
class GraphQLBusinessController {
    private final BusinessRetrievalClient businessRetrievalClient;
    private final BusinessManagementClient businessManagementClient;

    // ================= Queries and Mutations ================= //

    @QueryMapping
    Mono<Page<BusinessDTO>> businesses(@Argument long page, @Argument long pageSize) {
        return businessRetrievalClient.activeBusinesses(page, pageSize);
    }

    @QueryMapping
    Mono<Page<BusinessDTO>> myBusinesses(@Argument long page, @Argument long pageSize, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return businessRetrievalClient.myBusinesses(page, pageSize, requester);
    }

    @QueryMapping
    Mono<BusinessDTO> business(@Argument long businessId, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String requester) {
        return businessRetrievalClient.byId(businessId, requester);
    }

    @QueryMapping
    Mono<Page<BusinessDTO>> businessesByOwner(@Argument String owner, @Argument long page, @Argument long pageSize) {
        return businessRetrievalClient.byOwner(owner, page, pageSize);
    }

    @MutationMapping
    Mono<BusinessDTO> createBusiness(@Argument BusinessCreationDTO input, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return businessManagementClient.create(input, requester);
    }

    @MutationMapping
    Mono<BusinessDTO> changeBusinessDetails(@Argument long businessId, @Argument BusinessDetailsDTO input, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return businessManagementClient.updateDetails(businessId, input, requester);
    }

    @MutationMapping
    Mono<BusinessDTO> changeBusinessName(@Argument long businessId, @Argument String name, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return businessManagementClient.changeName(businessId, name, requester);
    }

    @MutationMapping
    Mono<BusinessDTO> changeBusinessActiveness(@Argument long businessId, @Argument boolean active, @ContextValue(HttpHeaders.AUTHORIZATION) String requester) {
        return businessManagementClient.changeActive(businessId, active, requester);
    }

    // ================= Queries and Mutations ================= //


    // ================= Relation from Staff ================= //

    @BatchMapping(typeName = "Staff", field = "business")
    Mono<Map<StaffDTO, BusinessDTO>> businessesByStaff(List<StaffDTO> staffs, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String requester) {
        return mapEntitiesToRelatedData(
                staffs,
                BusinessDTO::id,
                staff -> staff.business().id(),
                ids -> businessRetrievalClient.byIds(ids, requester)
        );
    }

    // ================= Relation from Staff ================= //


    // ================= Relation from Service ================= //

    @BatchMapping(typeName = "Service", field = "business")
    Mono<Map<ServiceDTO, BusinessDTO>> businessesByService(List<ServiceDTO> services, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String requester) {
        return mapEntitiesToRelatedData(
                services,
                BusinessDTO::id,
                service -> service.business().id(),
                ids -> businessRetrievalClient.byIds(ids, requester)
        );
    }

    // ================= Relation from Service ================= //
}
