package com.siliconflow.ai;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigManager {
    private static final String PREFS_NAME = "SiliconFlowAIConfig";
    private static final String KEY_API_URL = "api_url";
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_MODEL = "model";
    private static final String KEY_PROVIDER_NAME = "provider_name";
    
    private SharedPreferences prefs;
    
    public ConfigManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    
    public void saveConfig(Config config) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_API_URL, config.getApiUrl());
        editor.putString(KEY_API_KEY, config.getApiKey());
        editor.putString(KEY_MODEL, config.getModel());
        editor.putString(KEY_PROVIDER_NAME, config.getProviderName());
        editor.apply();
    }
    
    public Config getConfig() {
        String apiUrl = prefs.getString(KEY_API_URL, "");
        String apiKey = prefs.getString(KEY_API_KEY, "");
        String model = prefs.getString(KEY_MODEL, "");
        String providerName = prefs.getString(KEY_PROVIDER_NAME, "");
        
        if (apiKey.isEmpty()) {
            return null;
        }
        
        return new Config(apiUrl, apiKey, model, providerName);
    }
    
    public void clearConfig() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}