package com.backend.services;

import com.backend.exceptions.ServiceException;
import com.backend.models.dto.OrganizationDTO;
import com.backend.models.dto.UserDTO;
import com.backend.models.dto.request.CreateOrgRegistrationRequest;
import com.backend.models.entity.Organization;
import com.backend.models.entity.OrganizationChain;
import com.backend.models.entity.User;
import com.backend.models.enums.MessageLink;
import com.backend.models.enums.UserRole;
import com.backend.repositories.OrganizationRepo;
import com.backend.repositories.UserRepo;
import com.backend.util.MessageBundle;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrganizationSvc {

    private final OrganizationRepo organizationRepo;
    private final MessageBundle messageBundle;
    private final UserRepo userRepo;

    public boolean existsByName(String name) {
        return organizationRepo.existsByName(name);
    }

    public OrganizationDTO getById(UUID id) throws ServiceException {
        try {
            Organization organization = organizationRepo.getById(id);
            return new OrganizationDTO(organization);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }

    public Organization create(CreateOrgRegistrationRequest request) throws ServiceException {
        if (request == null || !request.isComplete()){
            throw new ServiceException(messageBundle.getMsg(MessageLink.BAD_REQUEST));
        }

        if (existsByName(request.getName())) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NAME_TAKEN));
        }

        long currentTime = System.currentTimeMillis();

        Organization organization = new Organization();
        organization.setName(request.getName());
        organization.setCountry(request.getCountry());
        organization.setCity(request.getCity());
        organization.setAddress(request.getAddress());
        organization.setPostcode(request.getPostcode());
        organization.setNumber(request.getNumber());
        organization.setEmail(request.getEmail());
        organization.setCreatedAt(currentTime);

        return organizationRepo.save(organization);
    }

    public Organization update(UUID id, CreateOrgRegistrationRequest request) throws ServiceException {
        if (request == null || !request.isComplete()){
            throw new ServiceException(messageBundle.getMsg(MessageLink.BAD_REQUEST));
        }

        Organization existingOrg = organizationRepo.findById(id)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        if (!existingOrg.getName().equals(request.getName())) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NAME_CHANGE_FORBIDDEN));
        }

        long currentTime = System.currentTimeMillis();

        existingOrg.setCountry(request.getCountry());
        existingOrg.setCity(request.getCity());
        existingOrg.setAddress(request.getAddress());
        existingOrg.setPostcode(request.getPostcode());
        existingOrg.setNumber(request.getNumber());
        existingOrg.setEmail(request.getEmail());
        existingOrg.setCreatedAt(currentTime);

        return organizationRepo.save(existingOrg);
    }

    public void delete(UUID id) throws ServiceException {
        try {

            Organization existingOrg = organizationRepo.findById(id)
                    .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

            organizationRepo.deleteById(id);

        } catch (Exception e){

            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));

        }
    }

    public List<UserDTO> getUsersInOrg(UUID organizationId) throws ServiceException {
        Organization organization = organizationRepo.findById(organizationId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        return organization.getManagers().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public Organization addUserInOrg(UUID organizationId, UUID userId) throws ServiceException {
        Organization organization = organizationRepo.findById(organizationId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        if (user.getOrganization() != null && !user.getOrganization().getId().equals(organizationId)) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NAME_TAKEN));
        }

        if (organization.getManagers().contains(user)) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.ALREADY_EXISTS));
        }

        organization.addUser(user);

        user.setUserRole(UserRole.MANAGER);
        user.setOrganization(organization);
        userRepo.save(user);

        return organizationRepo.save(organization);
    }

    public void removeUserFromOrg(UUID organizationId, UUID userId) throws ServiceException {
        Organization organization = organizationRepo.findById(organizationId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        if (user.getOrganization() == null || !user.getOrganization().getId().equals(organizationId)) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NAME_TAKEN));
        }

        organization.removeUser(user);

        user.setUserRole(null);
        user.setOrganization(null);

        userRepo.save(user);
        organizationRepo.save(organization);
    }
}
