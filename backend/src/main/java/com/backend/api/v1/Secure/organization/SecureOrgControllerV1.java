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

    /**
     * Fetches the details of an organization by its ID.
     *
     * This method retrieves the organization details based on the `id` parameter.
     * If the organization is found, it returns a `200 OK` response with the organization data.
     * If the organization is not found, it returns a `404 Not Found` response.
     * If any other error occurs, it returns a `400 Bad Request` response.
     *
     * @param id the ID of the organization.
     * @return a response entity containing the organization details or an error message.
     */
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

    /**
     * Creates a new organization.
     *
     * This method accepts a `CreateOrgRegistrationRequest` to create a new organization.
     * Upon successful creation, it returns a `201 Created` response with the organization data.
     * In case of an error, it returns a `400 Bad Request` response with the error message.
     *
     * @param request the request body containing the organization details.
     * @return a response entity with the created organization or an error message.
     */
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

    /**
     * Updates the details of an existing organization.
     *
     * This method updates the organization identified by `id` with new details provided in the `request`.
     * If the update is successful, it returns the updated organization with a `200 OK` status.
     * If the organization is not found, it returns a `404 Not Found` response.
     * In case of any other error, it returns a `400 Bad Request` response.
     *
     * @param id the ID of the organization to update.
     * @param request the request body containing the updated organization details.
     * @return a response entity with the updated organization or an error message.
     */
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

    /**
     * Deletes an organization by its ID.
     *
     * This method deletes the organization identified by the `id` parameter.
     * If the deletion is successful, it returns a `204 No Content` response.
     * If the organization is not found, it returns a `404 Not Found` response.
     * In case of any other error, it returns a `400 Bad Request` response.
     *
     * @param id the ID of the organization to delete.
     * @return a response entity indicating success or failure.
     */
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

    /**
     * Retrieves a list of users (managers) associated with an organization.
     *
     * This method fetches a list of managers/users within the organization identified by `organizationId`.
     * If successful, it returns a `200 OK` response with the list of users.
     * If the organization is not found or an error occurs, it returns an appropriate error message.
     *
     * @param organizationId the ID of the organization.
     * @return a response entity containing the list of managers or an error message.
     */
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

    /**
     * Adds a user (manager) to an organization.
     *
     * This method adds a user, identified by `managerId`, to the organization identified by `organizationId`.
     * If successful, it returns the updated organization with a `200 OK` response.
     * If the organization or user is not found, it returns a `404 Not Found` response.
     * In case of any other error, it returns a `400 Bad Request` response.
     *
     * @param organizationId the ID of the organization to add the user to.
     * @param managerId the ID of the user to add.
     * @return a response entity with the updated organization or an error message.
     */
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

    /**
     * Removes a user (manager) from an organization.
     *
     * This method removes a user, identified by `managerId`, from the organization identified by `organizationId`.
     * If successful, it returns a `204 No Content` response indicating the removal was successful.
     * If the organization or user is not found, it returns a `404 Not Found` response.
     * In case of any other error, it returns a `400 Bad Request` response.
     *
     * @param organizationId the ID of the organization.
     * @param managerId the ID of the user to remove.
     * @return a response entity indicating success or failure.
     */
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
