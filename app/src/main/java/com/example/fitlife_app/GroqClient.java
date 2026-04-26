package com.example.fitlife_app;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.*;

public class GroqClient {

    private static final String TAG = "GroqClient";
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL = "llama-3.1-8b-instant";

    private final OkHttpClient httpClient;
    private final String apiKey;
    private final JSONArray chatHistory;

    public interface GroqCallback {
        void onSuccess(String response);
        void onError(String error);
    }

    public GroqClient() {
        this.httpClient = new OkHttpClient();
        this.apiKey = BuildConfig.GROQ_API_KEY;
        this.chatHistory = new JSONArray();
    }

    public void sendMessage(String userMessage, GroqCallback callback) {
        if (apiKey == null || apiKey.isEmpty() || apiKey.equals("\"\"")) {
            callback.onError("API Key belum diset!");
            return;
        }

        try {
            // Build messages array
            JSONArray messages = new JSONArray();

            // System message
            JSONObject systemMsg = new JSONObject();
            systemMsg.put("role", "system");
            systemMsg.put("content", "Kamu adalah FitLife AI Coach, asisten fitness pribadi yang ramah. Selalu jawab dalam Bahasa Indonesia. Berikan saran latihan, nutrisi, dan motivasi yang helpful dan spesifik.");
            messages.put(systemMsg);

            // Add chat history
            for (int i = 0; i < chatHistory.length(); i++) {
                messages.put(chatHistory.get(i));
            }

            // Add current user message
            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);
            messages.put(userMsg);

            // Build request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", MODEL);
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 1024);
            requestBody.put("temperature", 0.7);

            RequestBody body = RequestBody.create(
                    requestBody.toString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            Log.d(TAG, "Calling Groq API...");

            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Network Error: " + e.getMessage());
                    callback.onError("Koneksi gagal.");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBodyObj = response.body()) {
                        String responseStr = responseBodyObj != null ? responseBodyObj.string() : "";

                        if (!response.isSuccessful()) {
                            Log.e(TAG, "Server Error: " + response.code() + " - " + responseStr);
                            callback.onError("Error: " + response.code());
                            return;
                        }

                        JSONObject json = new JSONObject(responseStr);
                        String botResponse = json.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");

                        // Update history
                        JSONObject userHistory = new JSONObject();
                        userHistory.put("role", "user");
                        userHistory.put("content", userMessage);
                        chatHistory.put(userHistory);

                        JSONObject assistantHistory = new JSONObject();
                        assistantHistory.put("role", "assistant");
                        assistantHistory.put("content", botResponse);
                        chatHistory.put(assistantHistory);

                        callback.onSuccess(botResponse.trim());

                    } catch (Exception e) {
                        Log.e(TAG, "Parse Error: " + e.getMessage());
                        callback.onError("Gagal memproses AI.");
                    }
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Fatal Error: " + e.getMessage());
            callback.onError("Kesalahan teknis.");
        }
    }
}