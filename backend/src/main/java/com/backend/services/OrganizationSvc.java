package com.backend.services;

import com.backend.exceptions.ServiceException;
import com.backend.models.dto.OrganizationDTO;
import com.backend.models.dto.UserDTO;
import com.backend.models.enums.UserAuthority;
import com.backend.models.rest.request.CreateOrgRegistrationRequest;
import com.backend.models.entity.Organization;
import com.backend.models.entity.User;
import com.backend.models.enums.MessageLink;
import com.backend.repositories.OrganizationRepo;
import com.backend.repositories.UserRepo;
import com.backend.util.MessageBundle;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing organizations, including creating, updating, deleting organizations,
 * adding/removing users, and fetching organization-related details.
 */
@Service
@AllArgsConstructor
public class OrganizationSvc {

    private final OrganizationRepo organizationRepo;
    private final MessageBundle messageBundle;
    private final UserRepo userRepo;

    /**
     * Checks if an organization with the specified name already exists.
     *
     * @param name the name of the organization to check.
     * @return true if the organization exists, false otherwise.
     */
    public boolean existsByName(String name) {
        return organizationRepo.existsByName(name);
    }

    /**
     * Retrieves an organization by its ID.
     *
     * @param id the UUID of the organization.
     * @return an {@link OrganizationDTO} object representing the organization.
     * @throws ServiceException if the organization is not found.
     */
    public OrganizationDTO getById(UUID id) throws ServiceException {
        try {
            Organization organization = organizationRepo.getById(id);
            return new OrganizationDTO(organization);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }

    /**
     * Creates a new organization with the provided details.
     *
     * @param request the request object containing organization details.
     * @return the created {@link Organization} entity.
     * @throws ServiceException if the request is incomplete or the organization name is already taken.
     */
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

    /**
     * Updates an existing organization with the provided details.
     *
     * @param id the UUID of the organization to update.
     * @param request the request object containing the new organization details.
     * @return the updated {@link Organization} entity.
     * @throws ServiceException if the request is incomplete, the organization is not found, or the name is being changed.
     */
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

    /**
     * Deletes an organization by its ID.
     *
     * @param id the UUID of the organization to delete.
     * @throws ServiceException if the organization is not found.
     */
    public void delete(UUID id) throws ServiceException {
        try {
            Organization existingOrg = organizationRepo.findById(id)
                    .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

            organizationRepo.deleteById(id);
        } catch (Exception e){
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }

    /**
     * Retrieves a list of users associated with a given organization.
     *
     * @param organizationId the UUID of the organization.
     * @return a list of {@link UserDTO} objects representing the users in the organization.
     * @throws ServiceException if the organization is not found.
     */
    public List<UserDTO> getUsersInOrg(UUID organizationId) throws ServiceException {
        Organization organization = organizationRepo.findById(organizationId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        return organization.getManagers().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Adds a user to an organization, assigning them the manager role.
     *
     * @param organizationId the UUID of the organization.
     * @param userId the UUID of the user to add.
     * @return the updated {@link Organization} entity.
     * @throws ServiceException if the organization or user is not found,
     *                          the user is already assigned to a different organization,
     *                          or the user is already part of the organization.
     */
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

        user.addAuthority(UserAuthority.MANAGER);
        user.setOrganization(organization);
        userRepo.save(user);

        return organizationRepo.save(organization);
    }

    /**
     * Removes a user from an organization, downgrading their role from manager to user.
     *
     * @param organizationId the UUID of the organization.
     * @param userId the UUID of the user to remove.
     * @throws ServiceException if the organization or user is not found,
     *                          or the user is not part of the specified organization.
     */
    public void removeUserFromOrg(UUID organizationId, UUID userId) throws ServiceException {
        Organization organization = organizationRepo.findById(organizationId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        if (user.getOrganization() == null || !user.getOrganization().getId().equals(organizationId)) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NAME_TAKEN));
        }

        organization.removeUser(user);

        user.removeAuthority(UserAuthority.MANAGER);
        user.addAuthority(UserAuthority.USER);
        user.setOrganization(null);

        userRepo.save(user);
        organizationRepo.save(organization);
    }
}
