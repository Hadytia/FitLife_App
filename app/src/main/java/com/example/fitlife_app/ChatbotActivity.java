package com.example.fitlife_app;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView      rvChat;
    private EditText          etMessage;
    private ImageButton       btnSend, btnClose;
    private LinearLayout      layoutQuickQuestions;

    private ChatAdapter       chatAdapter;
    private List<ChatMessage> messageList;
    private GeminiClient      geminiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        initViews();
        initChat();
        initQuickChips();
        setupSendButton();
    }

    // ── Init Views ───────────────────────────────────────────
    private void initViews() {
        rvChat               = findViewById(R.id.rvChat);
        etMessage            = findViewById(R.id.etMessage);
        btnSend              = findViewById(R.id.btnSend);
        btnClose             = findViewById(R.id.btnClose);
        layoutQuickQuestions = findViewById(R.id.layoutQuickQuestions);

        btnClose.setOnClickListener(v -> finish());
    }

    // ── Init RecyclerView & welcome message ──────────────────
    private void initChat() {
        messageList  = new ArrayList<>();
        chatAdapter  = new ChatAdapter(messageList);
        geminiClient = new GeminiClient();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rvChat.setLayoutManager(layoutManager);
        rvChat.setAdapter(chatAdapter);

        // Pesan sambutan
        addBotMessage(getString(R.string.coach_ai_welcome_1));
        addBotMessage(getString(R.string.coach_ai_welcome_2));
    }

    // ── Quick Question Chips ─────────────────────────────────
    private void initQuickChips() {
        TextView chipWorkout  = findViewById(R.id.chipWorkout);
        TextView chipCalories = findViewById(R.id.chipCalories);
        TextView chipDiet     = findViewById(R.id.chipDiet);
        TextView chipSleep    = findViewById(R.id.chipSleep);
        TextView chipProgress = findViewById(R.id.chipProgress);
        TextView chipHeart    = findViewById(R.id.chipHeart);

        chipWorkout .setOnClickListener(v -> sendQuickQuestion("Buatkan program workout plan untuk pemula"));
        chipCalories.setOnClickListener(v -> sendQuickQuestion("Bagaimana cara membakar kalori dengan efektif?"));
        chipDiet    .setOnClickListener(v -> sendQuickQuestion("Berikan tips healthy diet untuk mendukung olahraga"));
        chipSleep   .setOnClickListener(v -> sendQuickQuestion("Berapa lama waktu tidur dan recovery yang ideal?"));
        chipProgress.setOnClickListener(v -> sendQuickQuestion("Bagaimana cara track progress latihan yang benar?"));
        chipHeart   .setOnClickListener(v -> sendQuickQuestion("Tips menjaga kesehatan jantung melalui olahraga"));
    }

    // ── Setup Send Button ────────────────────────────────────
    private void setupSendButton() {
        btnSend.setOnClickListener(v -> handleSend());

        etMessage.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                handleSend();
                return true;
            }
            return false;
        });
    }

    // ── Handle Send ──────────────────────────────────────────
    private void handleSend() {
        String text = etMessage.getText().toString().trim();
        if (text.isEmpty()) return;

        etMessage.setText("");
        sendQuickQuestion(text);
    }

    // ── Kirim pesan ke Gemini ────────────────────────────────
    private void sendQuickQuestion(String question) {
        // Tampilkan pesan user
        addUserMessage(question);

        // Sembunyikan quick questions setelah pertama kali chat
        layoutQuickQuestions.setVisibility(View.GONE);

        // Tampilkan typing indicator
        addBotMessage("...");
        int typingIndex = messageList.size() - 1;

        // Kirim ke Gemini API
        geminiClient.sendMessage(question, new GeminiClient.GeminiCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    // Ganti typing indicator dengan respons asli
                    messageList.get(typingIndex).setMessage(response);
                    chatAdapter.notifyItemChanged(typingIndex);
                    scrollToBottom();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    messageList.get(typingIndex).setMessage(error);
                    chatAdapter.notifyItemChanged(typingIndex);
                    scrollToBottom();
                });
            }
        });
    }

    // ── Helper: tambah pesan ─────────────────────────────────
    private void addBotMessage(String message) {
        messageList.add(new ChatMessage(message, ChatMessage.TYPE_BOT));
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        scrollToBottom();
    }

    private void addUserMessage(String message) {
        messageList.add(new ChatMessage(message, ChatMessage.TYPE_USER));
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        scrollToBottom();
    }

    private void scrollToBottom() {
        rvChat.smoothScrollToPosition(messageList.size() - 1);
    }
}