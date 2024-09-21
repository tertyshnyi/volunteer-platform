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

@Service
@AllArgsConstructor
public class OrganizationChainSvc {

    private final MessageBundle messageBundle;

    private final OrganizationChainRepo chainRepo;
    private final OrganizationRepo organizationRepo;

    public OrganizationChain createOrgChain(String name) {
        OrganizationChain chain = new OrganizationChain(name);
        return chainRepo.save(chain);
    }

    public List<OrganizationDTO> getOrganizationsInChain(UUID chainId) throws ServiceException {
        OrganizationChain chain = chainRepo.findById(chainId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        return chain.getOrganizations().stream()
                .map(OrganizationDTO::new)
                .collect(Collectors.toList());
    }

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

    public void removeChain(UUID id) throws ServiceException {
        OrganizationChain organizationChain = chainRepo.findById(id)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        chainRepo.delete(organizationChain);
    }
}
