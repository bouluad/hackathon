package com.stockholders.carnetdordre.domain;

public final class ProductBuilder {
    private String id;
    private String name;
    private String slug;
    private Long price;
    private Long number;

    private ProductBuilder() {
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public ProductBuilder withPrice(Long price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withNumber(Long number) {
        this.number = number;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setSlug(slug);
        product.setPrice(price);
        product.setNumber(number);
        return product;
    }
}
