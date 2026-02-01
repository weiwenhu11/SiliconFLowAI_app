package com.siliconflow.ai;

public class Config {
    private String apiUrl;
    private String apiKey;
    private String model;
    private String providerName;
    
    public Config(String apiUrl, String apiKey, String model, String providerName) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.model = model;
        this.providerName = providerName;
    }
    
    public String getApiUrl() {
        return apiUrl;
    }
    
    public String getApiKey() {
        return apiKey;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getProviderName() {
        return providerName;
    }
    
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
    
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}