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

@Service
@AllArgsConstructor
public class UserSvc {

    private final UserRepo userRepo;
    private final MessageBundle messageBundle;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisSvc redisSvc;

    public final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);


    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public UserDTO getById(UUID id) throws ServiceException {
        try {
            User user = userRepo.getById(id);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }

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

    public void delete(UUID id) throws ServiceException {
        try {
            User existingUser = userRepo.findById(id)
                    .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

            userRepo.deleteById(existingUser.getId());

        } catch (Exception e) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }

    public UserConfidential getConfidentialUserById(UUID id) {
        return userRepo.getUserConfidentialById(id);
    }

    public UserConfidential getConfidentialUserByEmail(String email) {
        return userRepo.getUserConfidentialByEmail(email);
    }

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
