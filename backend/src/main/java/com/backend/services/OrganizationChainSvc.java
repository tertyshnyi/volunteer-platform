package com.backend.services;

import com.backend.exceptions.ServiceException;
import com.backend.models.dto.OrganizationDTO;
import com.backend.models.entity.Organization;
import com.backend.models.entity.OrganizationChain;
import com.backend.models.enums.MessageLink;
import com.backend.repositories.OrganizationChainRepo;
import com.backend.repositories.OrganizationRepo;
import com.backend.util.MessageBundle;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing organization chains, including creating chains, adding/removing organizations,
 * and retrieving organizations within a chain.
 * The service interacts with {@link OrganizationChainRepo}, {@link OrganizationRepo}, and {@link MessageBundle}.
 */
@Service
@AllArgsConstructor
public class OrganizationChainSvc {

    private final MessageBundle messageBundle;

    private final OrganizationChainRepo chainRepo;
    private final OrganizationRepo organizationRepo;

    /**
     * Creates a new organization chain with the specified name.
     *
     * @param name the name of the organization chain to be created.
     * @return the created {@link OrganizationChain} entity.
     */
    public OrganizationChain createOrgChain(String name) {
        OrganizationChain chain = new OrganizationChain(name);
        return chainRepo.save(chain);
    }

    /**
     * Retrieves a list of organizations associated with a given organization chain.
     *
     * @param chainId the UUID of the organization chain.
     * @return a list of {@link OrganizationDTO} objects representing the organizations in the chain.
     * @throws ServiceException if the organization chain is not found.
     */
    public List<OrganizationDTO> getOrganizationsInChain(UUID chainId) throws ServiceException {
        OrganizationChain chain = chainRepo.findById(chainId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        return chain.getOrganizations().stream()
                .map(OrganizationDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Adds an organization to an existing organization chain.
     *
     * @param chainId the UUID of the organization chain.
     * @param organizationId the UUID of the organization to be added.
     * @return the updated {@link OrganizationChain} entity after adding the organization.
     * @throws ServiceException if the organization chain or the organization is not found,
     *                          or if the organization is already part of a different chain or exists in the current chain.
     */
    public OrganizationChain addOrganizationToChain(UUID chainId, UUID organizationId) throws ServiceException {
        OrganizationChain organizationChain = chainRepo.findById(chainId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        Organization organization = organizationRepo.findById(organizationId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        if (organization.getChain() != null && !organization.getChain().getId().equals(chainId)) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NAME_TAKEN));
        }

        if (organizationChain.getOrganizations().contains(organization)) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.ALREADY_EXISTS));
        }

        organizationChain.addOrganization(organization);

        return chainRepo.save(organizationChain);
    }


    /**
     * Removes an organization from an existing organization chain.
     *
     * @param chainId the UUID of the organization chain.
     * @param organizationId the UUID of the organization to be removed.
     * @throws ServiceException if the organization chain or the organization is not found,
     *                          or if the organization is not part of the specified chain.
     */
    public void removeOrganizationFromChain(UUID chainId, UUID organizationId) throws ServiceException {
        OrganizationChain organizationChain = chainRepo.findById(chainId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        Organization organization = organizationRepo.findById(organizationId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        if (organization.getChain() == null || !organization.getChain().getId().equals(chainId)) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NAME_TAKEN));
        }

        organizationChain.removeOrganization(organization);
        organizationRepo.save(organization);
        chainRepo.save(organizationChain);
    }

    /**
     * Removes an organization chain by its ID.
     *
     * @param id the UUID of the organization chain to be deleted.
     * @throws ServiceException if the organization chain is not found or deletion fails.
     */
    public void removeChain(UUID id) throws ServiceException {
        OrganizationChain organizationChain = chainRepo.findById(id)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        chainRepo.delete(organizationChain);
    }
}
