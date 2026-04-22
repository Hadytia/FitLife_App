package com.example.fitlife_app;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.*;

public class GeminiClient {

    private static final String TAG     = "GeminiClient";
    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/" +
                    "gemini-2.0-flash-001:generateContent?key=";

    private final OkHttpClient httpClient;
    private final String       apiKey;

    public interface GeminiCallback {
        void onSuccess(String response);
        void onError(String error);
    }

    public GeminiClient() {
        this.httpClient = new OkHttpClient();
        this.apiKey     = BuildConfig.GEMINI_API_KEY;
    }

    // ── Deteksi bahasa otomatis ──────────────────────────────
    private String detectLanguage(String message) {
        String lower = message.toLowerCase().trim();
        String[] idKeywords = {
                "saya","aku","kamu","tolong","bantu","berapa","latihan",
                "diet","berat","kalori","push","lari","yoga","makan",
                "tidur","olahraga","program","tips","cara","apa","gimana",
                "bagaimana","kenapa","kapan","dong","deh","ya","nih","yuk"
        };
        for (String kw : idKeywords) {
            if (lower.contains(kw)) return "id";
        }
        return "en";
    }

    // ── System prompt sesuai bahasa ─────────────────────────
    private String buildSystemPrompt(String lang) {
        String base =
                "You are FitLife AI Coach, a professional personal fitness assistant " +
                        "specialized in push-up training, running, and yoga. " +
                        "You give practical, motivating, and science-based fitness advice. " +
                        "Keep responses concise (max 3-4 sentences) and friendly.";

        if (lang.equals("id")) {
            return base +
                    " PENTING: Pengguna menulis dalam Bahasa Indonesia. " +
                    "Jawab SELALU dalam Bahasa Indonesia yang baik, ramah, dan mudah dipahami. " +
                    "Gunakan sapaan yang hangat dan emoji secukupnya.";
        }
        return base +
                " Always respond in clear, friendly English. " +
                "Use warm greetings and occasional emojis.";
    }

    // ── Kirim pesan ke Gemini API ────────────────────────────
    public void sendMessage(String userMessage, GeminiCallback callback) {
        String lang         = detectLanguage(userMessage);
        String systemPrompt = buildSystemPrompt(lang);

        try {
            JSONObject textPart = new JSONObject();
            textPart.put("text", systemPrompt + "\n\nUser: " + userMessage);

            JSONArray parts = new JSONArray();
            parts.put(textPart);

            JSONObject content = new JSONObject();
            content.put("parts", parts);

            JSONArray contents = new JSONArray();
            contents.put(content);

            JSONObject requestBody = new JSONObject();
            requestBody.put("contents", contents);

            // Generation config
            JSONObject genConfig = new JSONObject();
            genConfig.put("temperature", 0.7);
            genConfig.put("maxOutputTokens", 1024);
            requestBody.put("generationConfig", genConfig);

            RequestBody body = RequestBody.create(
                    requestBody.toString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(API_URL + apiKey)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "API call failed: " + e.getMessage());
                    callback.onError("Koneksi gagal. Periksa internet kamu ya!");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseBody = response.body().string();
                        JSONObject json     = new JSONObject(responseBody);

                        if (json.has("error")) {
                            String errMsg = json.getJSONObject("error")
                                    .getString("message");
                            callback.onError("Error: " + errMsg);
                            return;
                        }

                        String text = json
                                .getJSONArray("candidates")
                                .getJSONObject(0)
                                .getJSONObject("content")
                                .getJSONArray("parts")
                                .getJSONObject(0)
                                .getString("text");

                        callback.onSuccess(text.trim());

                    } catch (Exception e) {
                        Log.e(TAG, "Parse error: " + e.getMessage());
                        callback.onError("Gagal memproses respons AI.");
                    }
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Request build error: " + e.getMessage());
            callback.onError("Terjadi kesalahan. Coba lagi ya!");
        }
    }
}