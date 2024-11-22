package com.backend.services;

import com.backend.exceptions.ServiceException;
import com.backend.models.dto.UserDTO;
import com.backend.models.entity.UserConfidential;
import com.backend.models.rest.request.CreateLoginRequest;
import com.backend.models.rest.request.CreateUserRequest;
import com.backend.models.entity.User;
import com.backend.models.enums.MessageLink;
import com.backend.repositories.UserRepo;
import com.backend.util.EmailValidator;
import com.backend.util.JwtTokenUtil;
import com.backend.util.MessageBundle;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Service class that handles user-related operations, including
 * creation, update, deletion, authentication, and retrieval of user information.
 * It interacts with the repository to persist or fetch user data from the database
 * and provides additional features like password encryption and JWT token generation.
 * The class also manages user confidential data retrieval.
 */
@Service
@AllArgsConstructor
public class UserSvc {

    private final UserRepo userRepo;
    private final MessageBundle messageBundle;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisSvc redisSvc;

    public final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    /**
     * Checks if a user exists in the repository by their email.
     *
     * @param email the email to search for.
     * @return true if the user exists, false otherwise.
     */
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    /**
     * Retrieves a user by their unique ID and returns a UserDTO.
     *
     * @param id the UUID of the user.
     * @return the UserDTO object corresponding to the user ID.
     * @throws ServiceException if the user cannot be found.
     */
    public UserDTO getById(UUID id) throws ServiceException {
        try {
            User user = userRepo.getById(id);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }

    /**
     * Retrieves a user by their email and returns a UserDTO.
     *
     * @param email the email of the user.
     * @return the UserDTO object corresponding to the email.
     * @throws ServiceException if the user cannot be found or email is invalid.
     */
    public UserDTO getByEmail(String email) throws ServiceException {
        try {
            User user = userRepo.findByEmail(email);

            if (user == null) {
                throw new ServiceException(messageBundle.getMsg(MessageLink.BAD_REQUEST));
            }

            return new UserDTO(user);
        } catch (Exception e) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }

    /**
     * Creates a new user based on the provided CreateUserRequest.
     *
     * @param request the request containing user information.
     * @return the newly created User object.
     * @throws ServiceException if the request is invalid or email is already taken.
     */
    public User create(CreateUserRequest request) throws ServiceException {
        if (request == null || !request.isComplete()) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.BAD_REQUEST));
        }

        if (!EmailValidator.validate(request.getEmail())) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.EMAIL_INCORRECT));
        }

        if (existsByEmail(request.getEmail())) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.EMAIL_TAKEN));
        }

        long currentTime = System.currentTimeMillis();

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setCreatedAt(currentTime);

        return userRepo.save(user);
    }

    /**
     * Updates an existing user based on the provided UUID and CreateUserRequest.
     *
     * @param id the UUID of the user to update.
     * @param request the request containing the updated user information.
     * @return the updated User object.
     * @throws ServiceException if the user cannot be found or email is changed.
     */
    public User update(UUID id, CreateUserRequest request) throws ServiceException {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

        if (!existingUser.getEmail().equals(request.getEmail())) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.EMAIL_CHANGE_FORBIDDEN));
        }

        existingUser.setName(request.getName());
        existingUser.setSurname(request.getSurname());
        existingUser.setPassword(request.getPassword());
        existingUser.setPhoneNumber(request.getPhoneNumber());
        existingUser.setCreatedAt(System.currentTimeMillis());

        return userRepo.save(existingUser);
    }

    /**
     * Deletes a user by their UUID.
     *
     * @param id the UUID of the user to delete.
     * @throws ServiceException if the user cannot be found.
     */
    public void delete(UUID id) throws ServiceException {
        try {
            User existingUser = userRepo.findById(id)
                    .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

            userRepo.deleteById(existingUser.getId());

        } catch (Exception e) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }

    /**
     * Retrieves confidential information for a user by their unique ID.
     *
     * @param id the UUID of the user.
     * @return the UserConfidential object associated with the user ID.
     */
    public UserConfidential getConfidentialUserById(UUID id) {
        return userRepo.getUserConfidentialById(id);
    }

    /**
     * Retrieves confidential information for a user by their email.
     *
     * @param email the email of the user.
     * @return the UserConfidential object associated with the user's email.
     */
    public UserConfidential getConfidentialUserByEmail(String email) {
        return userRepo.getUserConfidentialByEmail(email);
    }

    /**
     * Authenticates a user based on their login credentials.
     *
     * @param loginRequest the login request containing email/phone number and password.
     * @return a JWT token if the login is successful.
     * @throws ServiceException if the login credentials are incorrect.
     */
    public String loginUser(CreateLoginRequest loginRequest) throws ServiceException {
        if (loginRequest == null || !loginRequest.isComplete()) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.BAD_REQUEST));
        }
        User user;

        user = userRepo.findByEmail(loginRequest.getEmailOrPhoneNumber());
        if (user == null)
            throw new ServiceException(messageBundle.getMsg(MessageLink.WRONG_LOGIN_EMAIL));

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return jwtTokenUtil.generateToken(user);
        } else {
            throw new ServiceException(messageBundle.getMsg(MessageLink.WRONG_LOGIN_PASSWORD));
        }
    }
}
