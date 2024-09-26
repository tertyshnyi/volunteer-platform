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

@Service
@AllArgsConstructor
public class NewsSvc {
    private final NewsRepo newsRepo;
    private final MessageBundle messageBundle;

    private boolean existByTitle(String title) {
        return newsRepo.existsByTitle(title);
    }

    public NewsDTO getById(UUID id) throws ServiceException {
        try {
            News news = newsRepo.findById(id)
                    .orElseThrow(() -> new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND)));
            return new NewsDTO(news);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(messageBundle.getMsg(MessageLink.NOT_FOUND));
        }
    }

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
