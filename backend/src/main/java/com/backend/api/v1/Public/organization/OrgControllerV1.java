package com.backend.api.v1.Public.organization;

import com.backend.exceptions.ServiceException;
import com.backend.models.dto.OrganizationDTO;
import com.backend.models.dto.request.CreateOrgRegistrationRequest;
import com.backend.models.entity.Organization;
import com.backend.models.enums.MessageLink;
import com.backend.models.rest.RestResponseBody;
import com.backend.models.rest.RestResponseEntity;
import com.backend.services.OrganizationSvc;
import com.backend.util.MessageBundle;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/public/organization")
public class OrgControllerV1 {

    private final OrganizationSvc organizationSvc;
    private final MessageBundle messageBundle;

    @GetMapping("/{id}")
    public RestResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable UUID id) {
        try {
            OrganizationDTO organizationDTO = organizationSvc.getById(id);
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, organizationDTO),
                    HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, messageBundle.getMsg(MessageLink.NOT_FOUND), null),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping
    public RestResponseEntity<Organization> createOrganization(@RequestBody CreateOrgRegistrationRequest request){
        try {
            Organization createdOrganization = organizationSvc.create(request);
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, createdOrganization),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PutMapping("/{id}")
    public RestResponseEntity<Organization> updateOrganization(@PathVariable UUID id, @RequestBody CreateOrgRegistrationRequest request){
        try {
            Organization updatedOrganization = organizationSvc.update(id, request);
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, updatedOrganization),
                    HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, messageBundle.getMsg(MessageLink.NOT_FOUND), null),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @DeleteMapping("/{id}")
    public RestResponseEntity<Void> deleteOrganization(@PathVariable UUID id) {
        try {
            organizationSvc.getById(id);
            organizationSvc.delete(id);

            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, null),
                    HttpStatus.NO_CONTENT
            );
        } catch (EntityNotFoundException e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, messageBundle.getMsg(MessageLink.NOT_FOUND), null),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
