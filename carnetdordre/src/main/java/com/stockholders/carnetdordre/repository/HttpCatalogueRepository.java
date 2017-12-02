package com.stockholders.carnetdordre.repository;

import com.stockholders.carnetdordre.domain.Product;
import com.stockholders.carnetdordre.security.SecurityUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class HttpCatalogueRepository {

    private RestTemplate restTemplate;

    public HttpCatalogueRepository() {
        this.restTemplate = new RestTemplate();
    }

    public Iterable<Product> findAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SecurityUtils.getCurrentUserJWT().get());
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<List<Product>> rateResponse =
            restTemplate.exchange("http://207.154.195.210:8080/catalogue/api/products",
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Product>>() {
                });
        List<Product> products = rateResponse.getBody();
        return products;
    }

    public void save(Product p) {
    }
}
