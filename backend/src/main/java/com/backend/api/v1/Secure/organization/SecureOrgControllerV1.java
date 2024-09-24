package com.backend.api.v1.Secure.organization;

import com.backend.exceptions.ServiceException;
import com.backend.models.dto.OrganizationDTO;
import com.backend.models.dto.UserDTO;
import com.backend.models.rest.request.CreateOrgRegistrationRequest;
import com.backend.models.entity.Organization;
import com.backend.models.enums.MessageLink;
import com.backend.models.rest.response.RestResponseBody;
import com.backend.models.rest.response.RestResponseEntity;
import com.backend.services.OrganizationSvc;
import com.backend.util.MessageBundle;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/secure/organization")
public class SecureOrgControllerV1 {

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

    @GetMapping("/{organizationId}/managers")
    public RestResponseEntity<List<UserDTO>> getUsersInOrg(@PathVariable UUID organizationId) {
        try {
            List<UserDTO> managers = organizationSvc.getUsersInOrg(organizationId);

            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, managers),
                    HttpStatus.OK
            );
        } catch (ServiceException e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false,  messageBundle.getMsg(MessageLink.NOT_FOUND), null),
                    HttpStatus.NOT_FOUND
            );
        } catch (Exception e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/{organizationId}/managers/{managerId}")
    public RestResponseEntity<OrganizationDTO> addUserToOrg(
            @PathVariable UUID organizationId,
            @PathVariable UUID managerId) {

        try {
            Organization updatedOrganization = organizationSvc.addUserInOrg(organizationId, managerId);
            OrganizationDTO responseDto = new OrganizationDTO(updatedOrganization);

            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, responseDto),
                    HttpStatus.OK
            );
        } catch (ServiceException e) {
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

    @DeleteMapping("/{organizationId}/managers/{managerId}")
    public RestResponseEntity<Void> removeUserFromOrg(@PathVariable UUID organizationId, @PathVariable UUID managerId) {
        try {
            organizationSvc.removeUserFromOrg(organizationId, managerId);

            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, null),
                    HttpStatus.NO_CONTENT
            );
        } catch (ServiceException e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false,  messageBundle.getMsg(MessageLink.NOT_FOUND), null),
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
