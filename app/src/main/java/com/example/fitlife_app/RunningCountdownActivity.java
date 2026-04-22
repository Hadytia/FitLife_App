package com.example.fitlife_app;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RunningCountdownActivity extends AppCompatActivity {

    private String selectedLanguage;
    private TextView tvTitle, tvCountdown;
    private View circleBackground;
    private Button btnBack, btnStart, btnRestart, btnMenu;
    private CountDownTimer countDownTimer;
    private boolean isCountdownRunning = false;
    private int currentCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_countdown);

        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) selectedLanguage = "ID";

        tvTitle          = findViewById(R.id.tv_countdown_title);
        tvCountdown      = findViewById(R.id.tv_countdown);
        circleBackground = findViewById(R.id.circle_background);
        btnBack          = findViewById(R.id.btn_back);
        btnStart         = findViewById(R.id.btn_start);
        btnRestart       = findViewById(R.id.btn_restart);
        btnMenu          = findViewById(R.id.btn_menu);

        tvTitle.setText(selectedLanguage.equals("ID") ? "Berlari" : "Running");
        resetToInitialState();

        btnBack   .setOnClickListener(v -> finish());
        btnStart  .setOnClickListener(v -> startCountdown());
        btnRestart.setOnClickListener(v -> resetCountdown());
        btnMenu   .setOnClickListener(v -> goToMenu());

        // FAB Coach AI
        FloatingActionButton fabCoachAI = findViewById(R.id.fabCoachAI);
        fabCoachAI.setOnClickListener(v ->
                startActivity(new Intent(RunningCountdownActivity.this, ChatbotActivity.class))
        );

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isCountdownRunning && countDownTimer != null) countDownTimer.cancel();
                finish();
            }
        });
    }

    private void resetToInitialState() {
        currentCount = 5;
        tvCountdown.setText("05");
        circleBackground.setBackgroundResource(R.drawable.circle_green);
        btnBack   .setVisibility(View.VISIBLE);
        btnStart  .setVisibility(View.VISIBLE);
        btnRestart.setVisibility(View.GONE);
        btnMenu   .setVisibility(View.GONE);
        isCountdownRunning = false;
    }

    private void startCountdown() {
        btnBack   .setVisibility(View.GONE);
        btnStart  .setVisibility(View.GONE);
        btnRestart.setVisibility(View.VISIBLE);
        btnMenu   .setVisibility(View.GONE);
        isCountdownRunning = true;

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override public void onTick(long ms) {
                currentCount = (int) (ms / 1000);
                tvCountdown.setText(String.format("%02d", currentCount));
            }
            @Override public void onFinish() {
                tvCountdown.setText("00");
                circleBackground.setBackgroundResource(R.drawable.circle_red);
                isCountdownRunning = false;
                new android.os.Handler().postDelayed(() -> showWorkoutFinishedDialog(), 2000);
            }
        }.start();
    }

    private void resetCountdown() {
        if (countDownTimer != null) countDownTimer.cancel();
        resetToInitialState();
    }

    private void goToMenu() {
        Intent intent = new Intent(this, ExerciseMenuActivity.class);
        intent.putExtra("LANGUAGE", selectedLanguage);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void showWorkoutFinishedDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_running_finished);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView tvMessage      = dialog.findViewById(R.id.tv_finished_message);
        Button btnDialogRestart = dialog.findViewById(R.id.btn_dialog_restart);
        Button btnDialogMenu    = dialog.findViewById(R.id.btn_dialog_menu);

        if (selectedLanguage.equals("ID")) {
            tvMessage.setText("Latihan selesai!\n\nLuar biasa! Kamu berhasil lari dengan baik. Setiap langkah membawa lebih dekat ke tujuanmu!");
            btnDialogRestart.setText("Mulai Ulang");
            btnDialogMenu.setText("Menu");
        } else {
            tvMessage.setText("Workout finished!\n\nAmazing! You crushed your running session. Every step brings you closer to your goals!");
            btnDialogRestart.setText("Restart");
            btnDialogMenu.setText("Menu");
        }

        btnDialogRestart.setOnClickListener(v -> { dialog.dismiss(); resetCountdown(); });
        btnDialogMenu   .setOnClickListener(v -> { dialog.dismiss(); goToMenu(); });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}