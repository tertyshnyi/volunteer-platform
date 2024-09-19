package com.backend.api.v1.Secure.chain;

import com.backend.exceptions.ServiceException;
import com.backend.models.dto.OrganizationChainDTO;
import com.backend.models.dto.OrganizationDTO;
import com.backend.models.dto.request.CreateOrgChainRegistrationRequest;
import com.backend.models.entity.OrganizationChain;
import com.backend.models.enums.MessageLink;
import com.backend.models.rest.RestResponseBody;
import com.backend.models.rest.RestResponseEntity;
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
public class OrgChainControllerV1 {

    private final OrganizationChainSvc organizationChainSvc;
    private final MessageBundle messageBundle;

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
