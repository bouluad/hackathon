package com.stockholders.carnetdordre.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stockholders")
public class StockHoldersConfiguration {

    private String apiGatewayUrl;

    public String getApiGatewayUrl() {
        return apiGatewayUrl;
    }

    public void setApiGatewayUrl(String apiGatewayUrl) {
        this.apiGatewayUrl = apiGatewayUrl;
    }
}
