package com.backend.api.v1.Public.user;

import com.backend.models.dto.UserDTO;
import com.backend.models.dto.request.CreateUserRequest;
import com.backend.models.entity.User;
import com.backend.models.enums.MessageLink;
import com.backend.models.rest.RestResponseBody;
import com.backend.models.rest.RestResponseEntity;
import com.backend.services.UserSvc;
import com.backend.util.MessageBundle;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/public/user")
public class UserControllerV1 {

    private final UserSvc userSvc;
    private final MessageBundle messageBundle;

    @GetMapping("/{id}")
    public RestResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        try {
            UserDTO userDTO = userSvc.getById(id);
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, userDTO),
                    HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, messageBundle.getMsg(MessageLink.NOT_FOUND), null),
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
    public RestResponseEntity<User> createUser(@RequestBody CreateUserRequest request){
        try {
            User createdUser = userSvc.create(request);
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, createdUser),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PutMapping("/{id}")
    public RestResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody CreateUserRequest request){
        try {
            User updatedUser = userSvc.update(id, request);
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, updatedUser),
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

    @DeleteMapping("/{id}")
    public RestResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        try {
            userSvc.getById(id);
            userSvc.delete(id);

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
}

