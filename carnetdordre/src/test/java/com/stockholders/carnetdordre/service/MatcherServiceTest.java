package com.stockholders.carnetdordre.service;

import com.stockholders.carnetdordre.domain.Product;
import com.stockholders.carnetdordre.domain.ProductBuilder;
import com.stockholders.carnetdordre.domain.Request;
import com.stockholders.carnetdordre.domain.RequestBuilder;
import com.stockholders.carnetdordre.repository.HttpCatalogueRepository;
import com.stockholders.carnetdordre.repository.RequestRepository;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MatcherServiceTest {

    public static final String T11_00_00_000Z = "2017-12-02T11:00:00.000z";

    @Test
    public void matches_with_no_request_should_do_nothing() {
        RequestRepository requestRepositoryMock = mock(RequestRepository.class);
        HttpCatalogueRepository catalogueRepositoryMock = mock(HttpCatalogueRepository.class);
        MatcherService matcherService = new MatcherService(requestRepositoryMock, catalogueRepositoryMock, null);

        //Given
        when(requestRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        when(catalogueRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        //When
        List<Request> requests = matcherService.matches();

        //Then
        assertThat(requests).isEmpty();
    }

    @Test
    public void matcherServiceUpdater_should_modify_stock_and_matchedDate() {
        //Given
        RequestRepository requestRepositoryMock = mock(RequestRepository.class);
        HttpCatalogueRepository catalogueRepositoryMock = mock(HttpCatalogueRepository.class);
        Clock clock = mock(Clock.class);
        MatcherService.MatcherServiceUpdater matcherServiceUpdater = new MatcherService.MatcherServiceUpdater(
            requestRepositoryMock, catalogueRepositoryMock, clock
        );

        //When
        Request request1 = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(390L).withUser("user1").withScore((double)42)
            .build();
        Product dummyProduct = new Product();
        Request matchedRequest = matcherServiceUpdater.doMatches(request1, dummyProduct);
        when(clock.instant()).thenReturn(Instant.parse(T11_00_00_000Z));

        //Then
        Request requestResult = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(390L).withUser("user1").withScore((double)42)
            .withMatchedAt(Date.from(Instant.parse(T11_00_00_000Z)))
            .build();
        assertThat(matchedRequest).isEqualToComparingFieldByFieldRecursively(requestResult);
        verify(requestRepositoryMock).save(argThat(new SamePropertyValuesAs<>(requestResult)));

        Product productStockUpdated = ProductBuilder.aProduct()
            .withId("1").withName("PS4").withNumber(0L).withPrice(390L).build();
        verify(catalogueRepositoryMock).save(argThat(new SamePropertyValuesAs<Product>(productStockUpdated)));
    }

    @Test
    public void one_stock_and_one_request_with_greater_price_and_should_match() {
        RequestRepository requestRepositoryMock = mock(RequestRepository.class);
        HttpCatalogueRepository catalogueRepositoryMock = mock(HttpCatalogueRepository.class);
        MatcherService.MatcherServiceUpdater matcherServiceUpdaterMock =
            mock(MatcherService.MatcherServiceUpdater.class);

        MatcherService matcherService = new MatcherService(requestRepositoryMock, catalogueRepositoryMock,
            matcherServiceUpdaterMock);

        //Given
        Request request1 = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(410L).withUser("user1").withScore((double)42)
            .build();
        List<Request> requests = Arrays.asList(request1);
        when(requestRepositoryMock.findAll()).thenReturn(requests);

        Product product1 = ProductBuilder.aProduct()
            .withId("1").withName("PS4").withNumber(1L).withPrice(390L).build();
        List<Product> products = Arrays.asList(product1);
        when(catalogueRepositoryMock.findAll()).thenReturn(products);

        Request requestResult = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(390L).withUser("user1").withScore((double)42)
            .withMatchedAt(Date.from(Instant.parse(T11_00_00_000Z)))
            .build();
        when(matcherServiceUpdaterMock.doMatches(anyObject(), anyObject())).thenReturn(requestResult);

        //When
        List<Request> matches = matcherService.matches();

        //Then
        verify(matcherServiceUpdaterMock).doMatches(
            argThat(new SamePropertyValuesAs<>(request1)),
            argThat(new SamePropertyValuesAs<>(product1))
        );
    }

    @Test
    public void one_stock_and_one_request_with_lower_price_and_should_match_request_with_higher_score() {
        RequestRepository requestRepositoryMock = mock(RequestRepository.class);
        HttpCatalogueRepository catalogueRepositoryMock = mock(HttpCatalogueRepository.class);
        MatcherService.MatcherServiceUpdater matcherServiceUpdaterMock =
            mock(MatcherService.MatcherServiceUpdater.class);

        MatcherService matcherService = new MatcherService(requestRepositoryMock, catalogueRepositoryMock,
            matcherServiceUpdaterMock);

        //Given
        Request request1 = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(410L).withUser("user1").withScore((double)100)
            .build();
        Request request2 = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(410L).withUser("user1").withScore((double)42)
            .build();
        List<Request> requests = Arrays.asList(request1, request2);
        when(requestRepositoryMock.findAll()).thenReturn(requests);

        Product product1 = ProductBuilder.aProduct()
            .withId("1").withName("PS4").withNumber(1L).withPrice(390L).build();
        List<Product> products = Arrays.asList(product1);
        when(catalogueRepositoryMock.findAll()).thenReturn(products);

        Request requestResult = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(410L).withUser("user1").withScore((double)100)
            .withMatchedAt(Date.from(Instant.parse(T11_00_00_000Z)))
            .build();
        when(matcherServiceUpdaterMock.doMatches(anyObject(), anyObject())).thenReturn(requestResult);

        //When
        List<Request> matches = matcherService.matches();

        //Then
        verify(matcherServiceUpdaterMock).doMatches(
            argThat(new SamePropertyValuesAs<>(request1)),
            argThat(new SamePropertyValuesAs<>(product1))
        );
    }

    @Test
    public void complex_text() {
        RequestRepository requestRepositoryMock = mock(RequestRepository.class);
        HttpCatalogueRepository catalogueRepositoryMock = mock(HttpCatalogueRepository.class);
        MatcherService.MatcherServiceUpdater matcherServiceUpdaterMock =
            mock(MatcherService.MatcherServiceUpdater.class);

        MatcherService matcherService = new MatcherService(requestRepositoryMock, catalogueRepositoryMock,
            matcherServiceUpdaterMock);

        //Given
        Request r_ps4_350_40 = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(350L).withUser("user1").withScore((double)40)
            .build();
        Request r_ps4_325_50 = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(325L).withUser("user1").withScore((double)50)
            .build();
        Request r_ps4_285_100 = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(285L).withUser("user1").withScore((double)100)
            .build();
        List<Request> requests = Arrays.asList(r_ps4_350_40, r_ps4_325_50, r_ps4_285_100);
        when(requestRepositoryMock.findAll()).thenReturn(requests);

        Product ps4_300 = ProductBuilder.aProduct()
            .withId("1").withName("PS4").withNumber(1L).withPrice(300L).build();
        Product ps4_250 = ProductBuilder.aProduct()
            .withId("1").withName("PS4").withNumber(1L).withPrice(250L).build();
        List<Product> products = Arrays.asList(ps4_300, ps4_250);
        when(catalogueRepositoryMock.findAll()).thenReturn(products);

        Request requestResult = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(285L).withUser("user1").withScore((double)100)
            .withMatchedAt(Date.from(Instant.parse(T11_00_00_000Z)))
            .build();
        when(matcherServiceUpdaterMock.doMatches(anyObject(), anyObject())).thenReturn(requestResult);

        Request requestResult2 = RequestBuilder.aRequest()
            .withId("1").withName("PS4").withPrice(325L).withUser("user1").withScore((double)100)
            .withMatchedAt(Date.from(Instant.parse(T11_00_00_000Z)))
            .build();
        when(matcherServiceUpdaterMock.doMatches(anyObject(), anyObject())).thenReturn(requestResult2);

        //When
        List<Request> matches = matcherService.matches();

        //Then
        verify(matcherServiceUpdaterMock).doMatches(
            argThat(new SamePropertyValuesAs<>(r_ps4_285_100)),
            argThat(new SamePropertyValuesAs<>(ps4_250))
        );

        verify(matcherServiceUpdaterMock).doMatches(
            argThat(new SamePropertyValuesAs<>(r_ps4_325_50)),
            argThat(new SamePropertyValuesAs<>(ps4_300))
        );
    }
}
