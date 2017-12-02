package com.stockholders.carnetdordre.repository;

import com.stockholders.carnetdordre.domain.Product;
import com.stockholders.carnetdordre.security.SecurityUtils;
import feign.Headers;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HttpCatalogueRepository {

    private RestTemplate restTemplate;

    public HttpCatalogueRepository() {
        this.restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
        list.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(list);
    }

    private HttpHeaders buildAuthenticationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SecurityUtils.getCurrentUserJWT().get());
        return headers;
    }

    public Iterable<Product> findAll() {
        HttpHeaders headers = buildAuthenticationHeader();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<List<Product>> rateResponse =
            restTemplate.exchange("http://localhost:8080/catalogue/api/products",
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Product>>() {
                });
        List<Product> products = rateResponse.getBody();
        return products;
    }

    public void save(Product p) {
        HttpHeaders headers = buildAuthenticationHeader();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> entity = new HttpEntity<Product>(p, headers);
        restTemplate.exchange("http://localhost:8080/catalogue/api/products",
            HttpMethod.PUT, entity, new ParameterizedTypeReference<Object>() {
            });
    }
}
