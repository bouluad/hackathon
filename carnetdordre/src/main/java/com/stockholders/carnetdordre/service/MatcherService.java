package com.stockholders.carnetdordre.service;

import com.stockholders.carnetdordre.domain.Product;
import com.stockholders.carnetdordre.domain.Request;
import com.stockholders.carnetdordre.repository.HttpCatalogueRepository;
import com.stockholders.carnetdordre.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class MatcherService {
    RequestRepository requestRepository;
    HttpCatalogueRepository httpCatalogueRepository;
    private Clock clock;
    private MatcherServiceUpdater matcherServiceUpdater;

    @Autowired
    public MatcherService(RequestRepository requestRepository,
                          HttpCatalogueRepository httpCatalogueRepository,
                          MatcherServiceUpdater matcherServiceUpdater) {
        this.requestRepository = requestRepository;
        this.httpCatalogueRepository = httpCatalogueRepository;
        this.matcherServiceUpdater = matcherServiceUpdater;
    }

    public List<Request> matches() {
        Iterable<Product> products = httpCatalogueRepository.findAll();
        assert products != null;

        List<Request> matchedRequests = new ArrayList<>();

        for (Product p : products) {
            Iterable<Request> requests = requestRepository.findAll();
            assert requests != null;

            Request matchedRequest = StreamSupport.stream(requests.spliterator(), false)
                .filter(
                    r -> r.getMatchedAt() == null
                    && p.getName().equals(r.getName())
                    && p.getPrice() < r.getPrice()
                    && p.getNumber() > 0)
                .max((r1, r2) -> r1.getScore().compareTo(r2.getScore()))
                .get();
            matcherServiceUpdater.doMatches(matchedRequest, p);
            matchedRequests.add(
                matchedRequest
            );
        }
        return matchedRequests;
    }

    static class MatcherServiceUpdater {
        RequestRepository requestRepository;
        HttpCatalogueRepository httpCatalogueRepository;
        private Clock clock;

        @Autowired
        public MatcherServiceUpdater(RequestRepository requestRepository,
                                     HttpCatalogueRepository httpCatalogueRepository,
                                     Clock clock) {
            this.requestRepository = requestRepository;
            this.httpCatalogueRepository = httpCatalogueRepository;
            this.clock = clock;
        }

        Request doMatches(Request r, Product p) {
            r.setMatchedAt(Date.from(Instant.now(clock)));
            requestRepository.save(r);
            p.setNumber(p.getNumber() - 1);
            httpCatalogueRepository.save(p);
            return r;
        }
    }
}
