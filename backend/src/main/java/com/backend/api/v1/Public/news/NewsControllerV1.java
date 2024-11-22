package com.backend.api.v1.Public.news;

import com.backend.models.dto.NewsDTO;
import com.backend.models.rest.request.CreateNewsRequest;
import com.backend.models.entity.News;
import com.backend.models.enums.MessageLink;
import com.backend.models.rest.response.RestResponseBody;
import com.backend.models.rest.response.RestResponseEntity;
import com.backend.services.NewsSvc;
import com.backend.util.MessageBundle;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/public/news")
public class NewsControllerV1 {

    private final NewsSvc newsSvc;
    private final MessageBundle messageBundle;

    /**
     * Get news by its ID.
     *
     * This method retrieves a single news item based on its unique identifier (UUID).
     * If the news item is found, it returns the news details in a `NewsDTO` format with a 200 OK status.
     * If not found, it returns a 404 NOT_FOUND status with an appropriate message.
     *
     * @param id the UUID of the news item to fetch.
     * @return ResponseEntity containing the news details or error message with corresponding HTTP status.
     */
    @GetMapping("/{id}")
    public RestResponseEntity<NewsDTO> getNewsById(@PathVariable UUID id) {
        try {
            NewsDTO newsDTO = newsSvc.getById(id);
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, newsDTO),
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

    /**
     * Create a new news entry.
     *
     * This method handles the creation of a new news item based on the provided request body (`CreateNewsRequest`).
     * If successful, it returns the created news with a 201 CREATED status.
     * In case of an error (e.g., validation failure), a 400 BAD_REQUEST status is returned.
     *
     * @param request the data required to create the news item.
     * @return ResponseEntity containing the created news or error message with corresponding HTTP status.
     */
    @PostMapping
    public RestResponseEntity<News> createNews(@RequestBody CreateNewsRequest request){
        try {
            News createdNews = newsSvc.create(request);
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, createdNews),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new RestResponseEntity<>(
                    new RestResponseBody<>(false, e.getMessage(), null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * Update an existing news entry.
     *
     * This method allows updating an existing news item based on its ID and the provided `CreateNewsRequest`.
     * If successful, it returns the updated news item with a 200 OK status.
     * If the news item does not exist, a 404 NOT_FOUND status is returned.
     *
     * @param id the UUID of the news item to update.
     * @param request the data to update the news item.
     * @return ResponseEntity containing the updated news or error message with corresponding HTTP status.
     */
    @PutMapping("/{id}")
    public RestResponseEntity<News> updateNews(@PathVariable UUID id, @RequestBody CreateNewsRequest request){
        try {
            News updatedNews = newsSvc.update(id, request);
            return new RestResponseEntity<>(
                    new RestResponseBody<>(true, null, updatedNews),
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

    /**
     * Delete a news entry by its ID.
     *
     * This method deletes an existing news item identified by the provided ID.
     * If the news item exists and is successfully deleted, it returns a 204 NO_CONTENT status.
     * If the news item is not found, a 404 NOT_FOUND status is returned.
     *
     * @param id the UUID of the news item to delete.
     * @return ResponseEntity with 204 NO_CONTENT status if successful or error message with corresponding HTTP status.
     */
    @DeleteMapping("/{id}")
    public RestResponseEntity<Void> deleteNews(@PathVariable UUID id) {
        try {
            newsSvc.getById(id);
            newsSvc.delete(id);

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
