package com.backend.api.v1.Public.auth;

import com.backend.exceptions.ServiceException;
import com.backend.models.rest.request.CreateLoginRequest;
import com.backend.models.rest.response.RestResponseBody;
import com.backend.services.UserSvc;
import com.backend.util.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/public/auth")
public class AuthControllerV1 {
    private final UserSvc userSvc;

    @PostMapping
    public ResponseEntity<RestResponseBody<?>> authUser(@RequestBody CreateLoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        try {
            String jwtToken = userSvc.loginUser(loginRequest);

            Cookie cookie = new Cookie(JwtTokenUtil.JWT_COOKIE_KEY, jwtToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(JwtTokenUtil.JWT_EXPIRATION_IN_SEC);

            httpServletResponse.addCookie(cookie);

            return new ResponseEntity<>(new RestResponseBody<>(true, null, jwtToken), HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(new RestResponseBody<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
