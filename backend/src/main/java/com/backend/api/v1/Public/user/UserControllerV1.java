package com.backend.api.v1.Public.user;

import com.backend.models.dto.UserConfidentialDTO;
import com.backend.models.dto.UserDTO;
import com.backend.models.entity.UserConfidential;
import com.backend.models.rest.request.CreateUserRequest;
import com.backend.models.entity.User;
import com.backend.models.enums.MessageLink;
import com.backend.models.enums.RedisKeyPrefix;
import com.backend.models.rest.response.RestResponseBody;
import com.backend.models.rest.response.RestResponseEntity;
import com.backend.services.RedisSvc;
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
    private final RedisSvc redisSvc;

    /**
     * Fetches the user information based on email or ID.
     *
     * This method allows the client to request user details using either the user's email or ID.
     * If both parameters are missing or empty, it returns a `400 Bad Request` response.
     *
     * If the user data exists in the Redis cache, it will be fetched from there to optimize response times.
     * If not, it will query the database for the user's confidential information.
     *
     * @param email the email of the user (optional)
     * @param id the ID of the user (optional)
     * @return a response entity with the user data if found, or an error message.
     */
    @GetMapping
    public RestResponseEntity<UserConfidentialDTO> getUser(
            @RequestParam(name="email", required = false) String email,
            @RequestParam(name="id", required = false) UUID id
            ) {

        if ((email == null || email.length() == 0) && id == null) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, messageBundle.getMsg(MessageLink.BAD_REQUEST), null),
                    HttpStatus.BAD_REQUEST
            );
        }

//        UserConfidentialDTO cache = redisSvc.find(RedisKeyPrefix.USER_DTO_BY_EMAIL, email, UserConfidentialDTO.class);
//        if (cache != null) {
//            return new RestResponseEntity<>(
//                    new RestResponseBody<>(true, null, cache),
//                    HttpStatus.OK
//            );
//        }

        try {
            UserConfidential user = userSvc.getConfidentialUserByEmail(email);
            if (user != null) {
                UserConfidentialDTO dto = new UserConfidentialDTO(user);
//                redisSvc.save(RedisKeyPrefix.USER_DTO_BY_EMAIL, email, dto);
                return new RestResponseEntity<>(
                        new RestResponseBody<>(true, null, dto),
                        HttpStatus.OK
                );
            } else {
                return new RestResponseEntity<>(
                        new RestResponseBody<>(true, messageBundle.getMsg(MessageLink.NOT_FOUND), null),
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * Creates a new user.
     *
     * This method accepts a request body containing the details of the user to be created and passes it to the service layer.
     * If the user creation is successful, it returns a `201 Created` response with the newly created user.
     * In case of any errors, it returns a `400 Bad Request` response with the error message.
     *
     * @param request the user creation request containing user details.
     * @return a response entity with the created user or an error message.
     */
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
}

