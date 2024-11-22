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

    /**
     * Endpoint for authenticating a user.
     *
     * This method handles a POST request to authenticate a user. It takes the login credentials (username and password)
     * from the request body, and then:
     * - Verifies the user's credentials.
     * - Generates a JWT token for successful authentication.
     * - Stores the JWT token in a cookie for use in future requests.
     *
     * If the authentication is successful, the method returns the token and a 200 OK status.
     * In case of an error (e.g., incorrect credentials), it returns an error message with a 400 BAD_REQUEST status.
     *
     * @param loginRequest object containing the login credentials (username and password)
     * @param httpServletResponse object to add the JWT token as a cookie in the response
     * @return ResponseEntity containing the response body and HTTP status:
     *         - 200 OK with the token in case of successful authentication
     *         - 400 BAD_REQUEST with an error message in case of failure
     */
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
