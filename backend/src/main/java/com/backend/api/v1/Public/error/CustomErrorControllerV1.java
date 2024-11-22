package com.backend.api.v1.Public.error;

import com.backend.models.enums.MessageLink;
import com.backend.models.rest.response.RestResponseBody;
import com.backend.util.MessageBundle;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/secure/error")
public class CustomErrorControllerV1 implements ErrorController {

    private final MessageBundle messageBundle;

    /**
     * Handles errors for the application.
     *
     * This method intercepts errors that occur within the application and returns a custom response
     * based on the HTTP status code. It looks up the error status code from the `HttpServletRequest`
     * and sends an appropriate error message in the response body.
     *
     * If the status code is `404 Not Found`, it returns a custom message indicating the resource was not found.
     * If the status code is `500 Internal Server Error`, it returns a message indicating a server error.
     * For any other status code, it returns a generic error message.
     *
     * @param request the `HttpServletRequest` object which contains information about the error.
     * @return ResponseEntity with a custom error message and corresponding HTTP status code.
     */
    public ResponseEntity<RestResponseBody<String>> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return new ResponseEntity<>(
                        new RestResponseBody<>(false, messageBundle.getMsg(MessageLink.NOT_FOUND), null),
                        HttpStatus.NOT_FOUND
                );
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ResponseEntity<>(
                        new RestResponseBody<>(false, messageBundle.getMsg(MessageLink.INTERNAL_ERROR), null),
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            }
        }

        return new ResponseEntity<>(
                new RestResponseBody<>(false, messageBundle.getMsg(MessageLink.UNKNOWN_ERROR), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

    }
}
