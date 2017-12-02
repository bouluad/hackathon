package com.stockholders.carnetdordre.service;

import com.stockholders.carnetdordre.domain.Request;
import com.stockholders.carnetdordre.repository.RequestRepository;
import com.stockholders.carnetdordre.security.SecurityUtils;
import net.bytebuddy.asm.Advice;
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
        if (request.getCreatedAt() == null) request.setCreatedAt(new Date(Calendar.getInstance().getTime().getTime()));
        if (request.getScore() == null) request.setScore(0.);
        request.setUser(SecurityUtils.getCurrentUserLogin().get());
        refreshScoring(request);
        return requestRepository.save(request);

    }

    private Request update(Request request) {
        return requestRepository.save(request);
    }

    private void refreshScoring(Request request) {
        List<Request> requests = findAll();
        long i = System.currentTimeMillis();
        log.debug("now " + i);
        for(Request req : requests){
            if (req.getName().equals(request.getName())){
                log.debug("created at " + req.getCreatedAt().getTime());
                req.setScore((i - req.getCreatedAt().getTime()) * 1d / i * Math.pow(req.getPrice(), 5));
                update(req);
            }
        }
        request.setScore((i - request.getCreatedAt().getTime()) * 1d / i * Math.pow(request.getPrice(), 5));
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
