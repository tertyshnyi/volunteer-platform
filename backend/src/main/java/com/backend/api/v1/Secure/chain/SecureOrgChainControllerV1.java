package com.backend.api.v1.Secure.chain;

import com.backend.exceptions.ServiceException;
import com.backend.models.dto.OrganizationChainDTO;
import com.backend.models.dto.OrganizationDTO;
import com.backend.models.rest.request.CreateOrgChainRegistrationRequest;
import com.backend.models.entity.OrganizationChain;
import com.backend.models.enums.MessageLink;
import com.backend.models.rest.response.RestResponseBody;
import com.backend.models.rest.response.RestResponseEntity;
import com.backend.services.OrganizationChainSvc;
import com.backend.util.MessageBundle;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/secure/chain")
public class SecureOrgChainControllerV1 {

    private final OrganizationChainSvc organizationChainSvc;
    private final MessageBundle messageBundle;

    /**
     * Fetches a list of organizations associated with a specific chain.
     *
     * This method retrieves all organizations that are part of a given organization chain,
     * identified by the `chainId`. If no organizations are found or an error occurs,
     * an appropriate response is returned.
     *
     * @param chainId the ID of the organization chain.
     * @return a response entity containing the list of organizations or an error message.
     */
    @GetMapping("/{chainId}/organizations")
    public RestResponseEntity<List<OrganizationDTO>> getOrganizationsInChain(@PathVariable UUID chainId) {
        try {
            List<OrganizationDTO> organizations = organizationChainSvc.getOrganizationsInChain(chainId);

            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, organizations),
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
     * Creates a new organization chain.
     *
     * This method creates a new organization chain with the specified name.
     * If the chain is successfully created, a `201 Created` response is returned.
     * In case of any errors, a `400 Bad Request` response is returned with an error message.
     *
     * @param request the request containing the name of the new organization chain.
     * @return a response entity containing the newly created chain or an error message.
     */
    @PostMapping
    public RestResponseEntity<OrganizationChain> createOrganizationChain(@RequestBody CreateOrgChainRegistrationRequest request) {
        try {
            OrganizationChain newChain = organizationChainSvc.createOrgChain(request.getName());

            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, newChain),
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
     * Adds an organization to an existing chain.
     *
     * This method adds a specific organization, identified by `organizationId`,
     * to an organization chain, identified by `chainId`. If the operation is successful,
     * it returns the updated organization chain. If not, it returns an appropriate error response.
     *
     * @param chainId the ID of the organization chain.
     * @param organizationId the ID of the organization to be added.
     * @return a response entity containing the updated chain or an error message.
     */
    @PostMapping("/{chainId}/organizations/{organizationId}")
    public RestResponseEntity<OrganizationChainDTO> addOrganizationToChain(
            @PathVariable UUID chainId,
            @PathVariable UUID organizationId) {

        try {
            OrganizationChain updatedChain = organizationChainSvc.addOrganizationToChain(chainId, organizationId);
            OrganizationChainDTO responseDto = new OrganizationChainDTO(updatedChain);

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
     * Removes an organization from a chain.
     *
     * This method removes a specific organization, identified by `organizationId`,
     * from an organization chain, identified by `chainId`. If the operation is successful,
     * it returns a `204 No Content` response indicating the successful deletion.
     *
     * @param chainId the ID of the organization chain.
     * @param organizationId the ID of the organization to be removed.
     * @return a response entity indicating success or failure.
     */
    @DeleteMapping("/{chainId}/organizations/{organizationId}")
    public RestResponseEntity<Void> removeOrganizationFromChain(@PathVariable UUID chainId, @PathVariable UUID organizationId) {
        try {
            organizationChainSvc.removeOrganizationFromChain(chainId, organizationId);

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

    /**
     * Removes an organization chain.
     *
     * This method deletes an organization chain, identified by `chainId`. If successful,
     * it returns a `204 No Content` response indicating the successful deletion of the chain.
     *
     * @param chainId the ID of the organization chain to be deleted.
     * @return a response entity indicating success or failure.
     */
    @DeleteMapping("/{chainId}")
    public RestResponseEntity<Void> removeChain(@PathVariable UUID chainId) {
        try {
            organizationChainSvc.removeChain(chainId);
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, null),
                    HttpStatus.NO_CONTENT
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
}
