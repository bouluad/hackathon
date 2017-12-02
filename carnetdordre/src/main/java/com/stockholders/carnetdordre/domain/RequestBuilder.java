package com.stockholders.carnetdordre.domain;

import java.util.Date;

public final class RequestBuilder {
    private String id;
    private String name;
    private Long price;
    private Double score;
    private String user;
    private Date createdAt;
    private Date matchedAt;

    private RequestBuilder() {
    }

    public static RequestBuilder aRequest() {
        return new RequestBuilder();
    }

    public RequestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public RequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RequestBuilder withPrice(Long price) {
        this.price = price;
        return this;
    }

    public RequestBuilder withScore(Double score) {
        this.score = score;
        return this;
    }

    public RequestBuilder withUser(String user) {
        this.user = user;
        return this;
    }

    public RequestBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public RequestBuilder withMatchedAt(Date matchedAt) {
        this.matchedAt = matchedAt;
        return this;
    }

    public Request build() {
        Request request = new Request();
        request.setId(id);
        request.setName(name);
        request.setPrice(price);
        request.setScore(score);
        request.setUser(user);
        request.setCreatedAt(createdAt);
        request.setMatchedAt(matchedAt);
        return request;
    }
}
