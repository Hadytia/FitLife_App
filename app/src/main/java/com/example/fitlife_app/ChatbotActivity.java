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
    private GroqClient        groqClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        initViews();
        initChat();
        initQuickChips();
        setupSendButton();
    }

    private void initViews() {
        rvChat               = findViewById(R.id.rvChat);
        etMessage            = findViewById(R.id.etMessage);
        btnSend              = findViewById(R.id.btnSend);
        btnClose             = findViewById(R.id.btnClose);
        layoutQuickQuestions = findViewById(R.id.layoutQuickQuestions);

        btnClose.setOnClickListener(v -> finish());
    }

    private void initChat() {
        messageList  = new ArrayList<>();
        chatAdapter  = new ChatAdapter(messageList);
        groqClient   = new GroqClient();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rvChat.setLayoutManager(layoutManager);
        rvChat.setAdapter(chatAdapter);

        addBotMessage(getString(R.string.coach_ai_welcome_1));
        addBotMessage(getString(R.string.coach_ai_welcome_2));
    }

    private void initQuickChips() {
        TextView chipWorkout  = findViewById(R.id.chipWorkout);
        TextView chipCalories = findViewById(R.id.chipCalories);
        TextView chipDiet     = findViewById(R.id.chipDiet);
        TextView chipSleep    = findViewById(R.id.chipSleep);
        TextView chipProgress = findViewById(R.id.chipProgress);
        TextView chipHeart    = findViewById(R.id.chipHeart);

        chipWorkout .setOnClickListener(v -> sendMessageToBot("Buatkan program workout plan untuk pemula"));
        chipCalories.setOnClickListener(v -> sendMessageToBot("Bagaimana cara membakar kalori dengan efektif?"));
        chipDiet    .setOnClickListener(v -> sendMessageToBot("Berikan tips healthy diet untuk mendukung olahraga"));
        chipSleep   .setOnClickListener(v -> sendMessageToBot("Berapa lama waktu tidur dan recovery yang ideal?"));
        chipProgress.setOnClickListener(v -> sendMessageToBot("Bagaimana cara track progress latihan yang benar?"));
        chipHeart   .setOnClickListener(v -> sendMessageToBot("Tips menjaga kesehatan jantung melalui olahraga"));
    }

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

    private void handleSend() {
        String text = etMessage.getText().toString().trim();
        if (text.isEmpty()) return;

        etMessage.setText("");
        sendMessageToBot(text);
    }

    private void sendMessageToBot(String question) {
        addUserMessage(question);

        if (layoutQuickQuestions.getVisibility() == View.VISIBLE) {
            layoutQuickQuestions.setVisibility(View.GONE);
        }

        addBotMessage("...");
        final int typingIndex = messageList.size() - 1;

        groqClient.sendMessage(question, new GroqClient.GroqCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    if (typingIndex >= 0 && typingIndex < messageList.size()) {
                        // ✅ formatResponse dipanggil di sini
                        messageList.get(typingIndex).setMessage(formatResponse(response));
                        chatAdapter.notifyItemChanged(typingIndex);
                    }
                    scrollToBottom();
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    if (typingIndex >= 0 && typingIndex < messageList.size()) {
                        messageList.get(typingIndex).setMessage(error);
                        chatAdapter.notifyItemChanged(typingIndex);
                    }
                    scrollToBottom();
                });
            }
        });
    }

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

    private String formatResponse(String response) {
        return response
                .replaceAll("\\*\\*(.*?)\\*\\*", "$1")
                .replaceAll("\\*(.*?)\\*", "$1")
                .replaceAll("#{1,6}\\s", "")
                .trim();
    }
}