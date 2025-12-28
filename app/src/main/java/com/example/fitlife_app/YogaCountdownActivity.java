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

public class YogaCountdownActivity extends AppCompatActivity {

    private String selectedLanguage;
    private TextView tvTitle;
    private TextView tvCountdown;
    private View circleBackground;
    private Button btnBack;
    private Button btnStart;
    private Button btnRestart;
    private Button btnMenu;

    private CountDownTimer countDownTimer;
    private boolean isCountdownRunning = false;
    private int currentCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_countdown);

        // Ambil bahasa yang dipilih
        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) {
            selectedLanguage = "ID";
        }

        // Inisialisasi views
        tvTitle = findViewById(R.id.tv_countdown_title);
        tvCountdown = findViewById(R.id.tv_countdown);
        circleBackground = findViewById(R.id.circle_background);
        btnBack = findViewById(R.id.btn_back);
        btnStart = findViewById(R.id.btn_start);
        btnRestart = findViewById(R.id.btn_restart);
        btnMenu = findViewById(R.id.btn_menu);

        // Set title berdasarkan bahasa
        tvTitle.setText("Yoga");

        // Set initial state
        resetToInitialState();

        // Button Back - kembali ke Yoga Instruction
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Button Start - mulai countdown
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountdown();
            }
        });

        // Button Restart - reset countdown ke 5
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCountdown();
            }
        });

        // Button Menu - kembali ke Exercise Menu
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YogaCountdownActivity.this, ExerciseMenuActivity.class);
                intent.putExtra("LANGUAGE", selectedLanguage);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        // Handle back button
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isCountdownRunning && countDownTimer != null) {
                    countDownTimer.cancel();
                }
                finish();
            }
        });
    }

    private void resetToInitialState() {
        currentCount = 5;
        tvCountdown.setText("05");
        circleBackground.setBackgroundResource(R.drawable.circle_green);

        // Show Back and Start buttons
        btnBack.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        btnRestart.setVisibility(View.GONE);
        btnMenu.setVisibility(View.GONE);

        isCountdownRunning = false;
    }

    private void startCountdown() {
        // Hide Back and Start, show only Restart
        btnBack.setVisibility(View.GONE);
        btnStart.setVisibility(View.GONE);
        btnRestart.setVisibility(View.VISIBLE);
        btnMenu.setVisibility(View.GONE);

        isCountdownRunning = true;

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentCount = (int) (millisUntilFinished / 1000);
                tvCountdown.setText(String.format("%02d", currentCount));
            }

            @Override
            public void onFinish() {
                currentCount = 0;
                tvCountdown.setText("00");

                // Change circle to red
                circleBackground.setBackgroundResource(R.drawable.circle_red);

                isCountdownRunning = false;

                // Jeda 2 detik sebelum menampilkan dialog
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showWorkoutFinishedDialog();
                    }
                }, 2000); // 2 detik
            }
        }.start();
    }

    private void resetCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        resetToInitialState();
    }

    private void showWorkoutFinishedDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yoga_finished);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView tvMessage = dialog.findViewById(R.id.tv_finished_message);
        Button btnDialogRestart = dialog.findViewById(R.id.btn_dialog_restart);
        Button btnDialogMenu = dialog.findViewById(R.id.btn_dialog_menu);

        // Set text berdasarkan bahasa
        if (selectedLanguage.equals("ID")) {
            tvMessage.setText("Latihan selesai!\n\nSelamat! Anda menyelesaikan sesi yoga dengan penuh kesadaran dan keseimbangan.");
        } else {
            tvMessage.setText("Workout finished!\n\nCongratulations! You finished your yoga session with mindfulness and balance.");
        }

        if (selectedLanguage.equals("ID")) {
            btnDialogRestart.setText("Mulai Ulang");
            btnDialogMenu.setText("Menu");
        } else {
            btnDialogRestart.setText("Restart");
            btnDialogMenu.setText("Menu");
        }

        // Restart button - kembali ke countdown 5
        btnDialogRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                resetCountdown();
            }
        });

        // Menu button - kembali ke Exercise Menu
        btnDialogMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(YogaCountdownActivity.this, ExerciseMenuActivity.class);
                intent.putExtra("LANGUAGE", selectedLanguage);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}