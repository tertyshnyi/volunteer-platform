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
                        new RestResponseBody<>(true, null, new UserConfidentialDTO(user)),
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

