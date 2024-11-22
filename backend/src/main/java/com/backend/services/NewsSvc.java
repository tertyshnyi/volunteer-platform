package com.backend.services;

import com.backend.exceptions.ServiceException;
import com.backend.models.dto.NewsDTO;
import com.backend.models.rest.request.CreateNewsRequest;
import com.backend.models.entity.News;
import com.backend.models.enums.MessageLink;
import com.backend.repositories.NewsRepo;
import com.backend.util.MessageBundle;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for handling news operations such as retrieving, creating, updating, and deleting news.
 * The service interacts with the {@link NewsRepo} repository to perform database operations and with
 * {@link MessageBundle} for retrieving localized error messages.
 */
@Service
@AllArgsConstructor
public class NewsSvc {

    private final NewsRepo newsRepo;
    private final MessageBundle messageBundle;

    /**
     * Check if a news article with the given title already exists in the database.
     *
     * @param title the title of the news article.
     * @return {@code true} if a news article with the title exists, otherwise {@code false}.
     */
    private boolean existByTitle(String title) {
        return newsRepo.existsByTitle(title);
    }

    /**
     * Retrieves a news article by its unique identifier.
     *
     * @param id the UUID of the news article.
     * @return a {@link NewsDTO} containing news details.
     * @throws ServiceException if the news article is not found.
     */
    public NewsDTO getById(UUID id) throws ServiceException {
        try {
            News news = newsRepo.findById(id)
                    .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));
            return new NewsDTO(news);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }

    /**
     * Creates a new news article and saves it to the database.
     *
     * @param request the {@link CreateNewsRequest} containing news details to be created.
     * @return the created {@link News} entity.
     * @throws ServiceException if the request is invalid or incomplete.
     */
    public News create(CreateNewsRequest request) throws ServiceException {
        if (request == null || !request.isComplete()) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.BAD_REQUEST));
        }

//        if (existByTitle(request.getTitle())) {
//            throw new ServiceException(messageBundle.getMsg(MessageLink.TITLE_TAKEN));
//        }

        long currentTime = System.currentTimeMillis();

        News news = new News();
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setFeaturedImage(request.getFeaturedImage());
        news.setAdditionalImages(request.getAdditionalImages());
        news.setCreatedAt(currentTime);

        return newsRepo.save(news);
    }

    /**
     * Updates an existing news article by its ID.
     *
     * @param id      the UUID of the news article to be updated.
     * @param request the {@link CreateNewsRequest} containing updated news details.
     * @return the updated {@link News} entity.
     * @throws ServiceException if the request is invalid or the news article is not found.
     */
    public News update(UUID id, CreateNewsRequest request) throws ServiceException {
        if (request == null || !request.isComplete()) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.BAD_REQUEST));
        }

        News existingNews = newsRepo.findById(id)
                .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));


        long currentTime = System.currentTimeMillis();
        existingNews.setTitle(request.getTitle());
        existingNews.setContent(request.getContent());
        existingNews.setFeaturedImage(request.getFeaturedImage());
        existingNews.setAdditionalImages(request.getAdditionalImages());
        existingNews.setCreatedAt(currentTime);

        return newsRepo.save(existingNews);
    }

    /**
     * Deletes a news article by its ID.
     *
     * @param id the UUID of the news article to be deleted.
     * @throws ServiceException if the news article is not found or deletion fails.
     */
    public void delete(UUID id) throws ServiceException {
        try {
            News existingNews = newsRepo.findById(id)
                    .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));

            newsRepo.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }
}
