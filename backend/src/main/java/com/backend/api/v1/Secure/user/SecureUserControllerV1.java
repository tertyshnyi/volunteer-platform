package com.backend.api.v1.Secure.user;

import com.backend.models.dto.UserConfidentialDTO;
import com.backend.models.entity.UserConfidential;
import com.backend.models.enums.RedisKeyPrefix;
import com.backend.models.rest.request.CreateUserRequest;
import com.backend.models.entity.User;
import com.backend.models.enums.MessageLink;
import com.backend.models.rest.response.RestResponseBody;
import com.backend.models.rest.response.RestResponseEntity;
import com.backend.services.RedisSvc;
import com.backend.services.UserSvc;
import com.backend.util.BearerHeaderExtractor;
import com.backend.util.CookieExtractor;
import com.backend.util.JwtTokenUtil;
import com.backend.util.MessageBundle;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/secure/user")
public class SecureUserControllerV1 {

    private final UserSvc userSvc;
    private final MessageBundle messageBundle;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisSvc redisSvc;

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

    @GetMapping("/me")
    public RestResponseEntity<UserConfidentialDTO> getUser(HttpServletRequest httpServletRequest) {
        String jwtToken = CookieExtractor.extractValue(httpServletRequest.getCookies(), JwtTokenUtil.JWT_COOKIE_KEY);
        if (jwtToken == null) {
            jwtToken = BearerHeaderExtractor.extract(httpServletRequest.getHeader("Authorization"));
        }

        if (jwtToken == null || !jwtTokenUtil.validateToken(jwtToken)) {
            return new RestResponseEntity<>(new RestResponseBody<>(false, messageBundle.getMsg(MessageLink.BAD_REQUEST), null), HttpStatus.UNAUTHORIZED);
        }

        UUID userId = jwtTokenUtil.getIdFromToken(jwtToken);

        if (userId == null) {
            return new RestResponseEntity<>(new RestResponseBody<>(false, messageBundle.getMsg(MessageLink.BAD_REQUEST), null), HttpStatus.UNAUTHORIZED);
        }

//        UserConfidentialDTO cache = redisSvc.find(RedisKeyPrefix.USER_DTO_BY_ID, userId.toString(), UserConfidentialDTO.class);
//        if (cache != null) {
//            return new RestResponseEntity<>(new RestResponseBody<>(true, null, cache), HttpStatus.OK);
//        }

        try {
            UserConfidential user = userSvc.getConfidentialUserById(userId);
            if (user != null) {
                UserConfidentialDTO dto = new UserConfidentialDTO(user);
//                redisSvc.save(RedisKeyPrefix.USER_DTO_BY_ID, userId.toString(), dto);
                return new RestResponseEntity<>(new RestResponseBody<>(true, null, dto), HttpStatus.OK);
            } else {
                return new RestResponseEntity<>(new RestResponseBody<>(true, messageBundle.getMsg(MessageLink.NOT_FOUND), null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new RestResponseEntity<>(new RestResponseBody<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
