package com.stockholders.carnetdordre.service;

import com.stockholders.carnetdordre.domain.Request;
import com.stockholders.carnetdordre.repository.RequestRepository;
import com.stockholders.carnetdordre.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Service Implementation for managing Request.
 */
@Service
public class RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    /**
     * Save a request.
     *
     * @param request the entity to save
     * @return the persisted entity
     */
    public Request save(Request request) {
        log.debug("Request to save Request : {}", request);
        request.setCreatedAt(new Date(Calendar.getInstance().getTime().getTime()));
        request.setScore(0.);
        request.setUser(SecurityUtils.getCurrentUserLogin().get());
        return requestRepository.save(request);
    }

    /**
     * Get all the requests.
     *
     * @return the list of entities
     */
    public List<Request> findAll() {
        log.debug("Request to get all Requests");
        return requestRepository.findAll();
    }

    /**
     * Get one request by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Request findOne(String id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findOne(id);
    }

    /**
     * Delete the request by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.delete(id);
    }
}
