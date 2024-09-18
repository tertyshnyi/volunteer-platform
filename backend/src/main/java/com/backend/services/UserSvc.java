package com.backend.services;

import com.backend.exceptions.ServiceException;
import com.backend.models.dto.UserDTO;
import com.backend.models.dto.request.CreateUserRequest;
import com.backend.models.entity.User;
import com.backend.models.enums.MessageLink;
import com.backend.repositories.UserRepo;
import com.backend.util.MessageBundle;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserSvc {

    private final UserRepo userRepo;
    private final MessageBundle messageBundle;
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

    public User create(CreateUserRequest request) throws ServiceException {
        if (request == null || !request.isComplete()) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.BAD_REQUEST));
        }

        if (existsByEmail(request.getEmail())) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.EMAIL_TAKEN));
        }

        long currentTime = System.currentTimeMillis();

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUserRole(request.getUserRole());
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
        existingUser.setUserRole(request.getUserRole());
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
}
