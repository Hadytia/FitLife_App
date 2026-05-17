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

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.content.Context;
import android.widget.Toast;

public class PushupCountdownActivity extends AppCompatActivity implements SensorEventListener {

    private String selectedLanguage;
    private TextView tvTitle, tvCountdown, tvLabel;
    private View circleBackground;
    private Button btnBack, btnStart, btnRestart, btnMenu;
    private boolean isWorkoutRunning = false;
    private int pushupCount = 0;

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private boolean isClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup_countdown);

        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) selectedLanguage = "ID";

        tvTitle          = findViewById(R.id.tv_countdown_title);
        tvCountdown      = findViewById(R.id.tv_countdown);
        tvLabel          = findViewById(R.id.tv_label_info);
        circleBackground = findViewById(R.id.circle_background);
        btnBack          = findViewById(R.id.btn_back);
        btnStart         = findViewById(R.id.btn_start);
        btnRestart       = findViewById(R.id.btn_restart);
        btnMenu          = findViewById(R.id.btn_menu);

        // Sensor Setup
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        tvTitle.setText("Push-Up");

        // Set Button Text based on Language
        if (btnBack != null) btnBack.setText(selectedLanguage.equals("ID") ? "KEMBALI" : "BACK");
        if (btnStart != null) btnStart.setText(selectedLanguage.equals("ID") ? "MULAI" : "START");
        if (btnRestart != null) btnRestart.setText(selectedLanguage.equals("ID") ? "MULAI ULANG" : "RESTART");
        if (btnMenu != null) btnMenu.setText(selectedLanguage.equals("ID") ? "MENU" : "MENU");

        resetToInitialState();

        btnBack   .setOnClickListener(v -> finish());
        btnStart  .setOnClickListener(v -> startWorkout());
        btnRestart.setOnClickListener(v -> resetWorkout());
        btnMenu   .setOnClickListener(v -> goToMenu());

        // FAB Coach AI
        FloatingActionButton fabCoachAI = findViewById(R.id.fabCoachAI);
        fabCoachAI.setOnClickListener(v ->
                startActivity(new Intent(PushupCountdownActivity.this, ChatbotActivity.class))
        );

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                stopWorkout();
                finish();
            }
        });
    }

    private void resetToInitialState() {
        pushupCount = 0;
        tvCountdown.setText("0");
        if (tvLabel != null) tvLabel.setText(selectedLanguage.equals("ID") ? "Siap Mulai?" : "Ready?");
        TextView tvSub = findViewById(R.id.tv_instruction_sub);
        if (tvSub != null) tvSub.setText(selectedLanguage.equals("ID") ? "Letakkan dada di atas HP" : "Place chest above phone");
        
        circleBackground.setBackgroundResource(R.drawable.circle_green);
        
        if (btnBack != null) {
            btnBack.setText(selectedLanguage.equals("ID") ? "KEMBALI" : "BACK");
            btnBack.setVisibility(View.VISIBLE);
        }
        if (btnStart != null) {
            btnStart.setText(selectedLanguage.equals("ID") ? "MULAI" : "START");
            btnStart.setVisibility(View.VISIBLE);
        }
        if (btnRestart != null) {
            btnRestart.setText(selectedLanguage.equals("ID") ? "MULAI ULANG" : "RESTART");
            btnRestart.setVisibility(View.GONE);
        }
        if (btnMenu != null) {
            btnMenu.setText(selectedLanguage.equals("ID") ? "MENU" : "MENU");
            btnMenu.setVisibility(View.GONE);
        }
        isWorkoutRunning = false;
    }

    private void startWorkout() {
        if (btnBack != null) btnBack.setVisibility(View.GONE);
        if (btnStart != null) btnStart.setVisibility(View.GONE);
        if (btnRestart != null) {
            btnRestart.setText(selectedLanguage.equals("ID") ? "MULAI ULANG" : "RESTART");
            btnRestart.setVisibility(View.VISIBLE);
        }
        if (btnMenu != null) {
            btnMenu.setText(selectedLanguage.equals("ID") ? "MENU" : "MENU");
            btnMenu.setVisibility(View.VISIBLE);
        }
        if (tvLabel != null) tvLabel.setText(selectedLanguage.equals("ID") ? "Lakukan Push-up!" : "Do Push-ups!");
        isWorkoutRunning = true;

        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, selectedLanguage.equals("ID") ? "Sensor tidak ditemukan!" : "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopWorkout() {
        isWorkoutRunning = false;
        sensorManager.unregisterListener(this);
    }

    private void resetWorkout() {
        stopWorkout();
        resetToInitialState();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!isWorkoutRunning) return;

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];
            
            // Logika deteksi: Dekat (Pushdown) lalu Jauh (Pushup)
            if (distance < proximitySensor.getMaximumRange()) {
                if (!isClose) {
                    isClose = true; // Dada mendekati HP
                }
            } else {
                if (isClose) {
                    // Berhasil satu repetisi!
                    pushupCount++;
                    tvCountdown.setText(String.valueOf(pushupCount));
                    vibrateEffect();
                    isClose = false;
                }
            }
        }
    }

    private void vibrateEffect() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            v.vibrate(100); // Getar singkat 100ms
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private void goToMenu() {
        stopWorkout();
        Intent intent = new Intent(this, ExerciseMenuActivity.class);
        intent.putExtra("LANGUAGE", selectedLanguage);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void showWorkoutFinishedDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pushup_finished);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView tvMessage      = dialog.findViewById(R.id.tv_finished_message);
        Button btnDialogRestart = dialog.findViewById(R.id.btn_dialog_restart);
        Button btnDialogMenu    = dialog.findViewById(R.id.btn_dialog_menu);

        if (selectedLanguage.equals("ID")) {
            tvMessage.setText("Latihan selesai!\n\nSelamat! Kamu menyelesaikan sesi push-up dengan kuat.");
            btnDialogRestart.setText("Mulai Ulang");
            btnDialogMenu.setText("Menu");
        } else {
            tvMessage.setText("Workout finished!\n\nCongratulations! You finished your push-up session strong.");
            btnDialogRestart.setText("Restart");
            btnDialogMenu.setText("Menu");
        }

        btnDialogRestart.setOnClickListener(v -> { dialog.dismiss(); resetWorkout(); });
        btnDialogMenu   .setOnClickListener(v -> { dialog.dismiss(); goToMenu(); });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopWorkout();
    }
}