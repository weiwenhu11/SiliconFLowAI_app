package com.siliconflow.ai;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApiService {
    private OkHttpClient client;
    
    public ApiService() {
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }
    
    public void chat(String message, Config config, Callback callback) {
        new Thread(() -> {
            try {
                String response = callAPI(message, config);
                callback.onSuccess(response);
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }
    
    private String callAPI(String message, Config config) throws IOException {
        // 构建JSON请求体
        JSONObject requestBody = new JSONObject();
        try {
            JSONArray messages = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", message);
            messages.put(userMessage);
            
            requestBody.put("messages", messages);
            requestBody.put("model", config.getModel());
            requestBody.put("stream", false);
            requestBody.put("max_tokens", 2048);
        } catch (Exception e) {
            throw new IOException("构建请求失败: " + e.getMessage());
        }
        
        // 构建请求
        Request request = new Request.Builder()
                .url(config.getApiUrl())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + config.getApiKey())
                .post(RequestBody.create(
                    requestBody.toString(),
                    MediaType.parse("application/json; charset=utf-8")
                ))
                .build();
        
        // 执行请求
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP错误: " + response.code() + " - " + response.message());
            }
            
            String responseBody = response.body().string();
            return parseResponse(responseBody);
        }
    }
    
    private String parseResponse(String responseBody) {
        try {
            JSONObject json = new JSONObject(responseBody);
            JSONArray choices = json.getJSONArray("choices");
            if (choices.length() > 0) {
                JSONObject choice = choices.getJSONObject(0);
                JSONObject message = choice.getJSONObject("message");
                return message.getString("content").trim();
            }
            return "API返回了空响应";
        } catch (Exception e) {
            // 尝试其他格式（如百度文心一言）
            try {
                JSONObject json = new JSONObject(responseBody);
                if (json.has("result")) {
                    return json.getString("result").trim();
                }
            } catch (Exception e2) {
                // 忽略
            }
            return "解析响应失败: " + e.getMessage();
        }
    }
    
    public interface Callback {
        void onSuccess(String response);
        void onError(String error);
    }
}