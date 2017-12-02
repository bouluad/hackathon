package com.stockholders.carnetdordre.domain;

public class Product {

    private String id;
    private String name;
    private String slug;
    private Long price;
    private Long number;

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Long getPrice() {
        return price;
    }

    public Long getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", slug='" + slug + '\'' +
            ", price=" + price +
            ", number=" + number +
            '}';
    }
}
