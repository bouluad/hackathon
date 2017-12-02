package com.stockholders.carnetdordre.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.stockholders.carnetdordre.domain.Request;
import com.stockholders.carnetdordre.service.MatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatchRessource {

    private final Logger log = LoggerFactory.getLogger(MatchRessource.class);

    private final MatcherService matcherService;

    public MatchRessource(MatcherService matcherService) {
        this.matcherService = matcherService;
    }

    @GetMapping("/api/match")
    @Timed
    public List<Request> getAllRequests() {
        log.debug("REST request to get all Machtes");
        return matcherService.matches();
    }
}
