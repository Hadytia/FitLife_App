package com.example.fitlife_app;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RunningCountdownActivity extends AppCompatActivity implements SensorEventListener {

    private String selectedLanguage;
    private TextView tvTitle, tvCountdown, tvLabel, tvSteps, tvDistance;
    private View circleBackground;
    private Button btnBack, btnStart, btnStop, btnMenu;
    
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private Sensor accelerometer;
    private boolean isTracking = false;
    
    // For Accelerometer fallback
    private float lastX, lastY, lastZ;
    private long lastUpdate = 0;
    private static final int SHAKE_THRESHOLD = 800;
    
    private int stepCount = 0;
    private long startTime = 0L;
    private Handler timerHandler;
    
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isTracking) return;
            long millis = SystemClock.uptimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            tvCountdown.setText(String.format("%02d:%02d", minutes, seconds));
            updateExtraInfo();
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_countdown);

        timerHandler = new Handler(Looper.getMainLooper());
        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) selectedLanguage = "ID";

        tvTitle          = findViewById(R.id.tv_countdown_title);
        tvCountdown      = findViewById(R.id.tv_countdown);
        tvLabel          = findViewById(R.id.tv_label_info);
        tvSteps          = findViewById(R.id.tv_steps_count);
        tvDistance       = findViewById(R.id.tv_distance_value);
        circleBackground = findViewById(R.id.circle_background);
        btnBack          = findViewById(R.id.btn_back);
        btnStart         = findViewById(R.id.btn_start);
        btnStop          = findViewById(R.id.btn_restart); 
        btnMenu          = findViewById(R.id.btn_menu);

        tvTitle.setText(selectedLanguage.equals("ID") ? "Berlari" : "Running");
        
        // Set Button Text based on Language
        if (btnBack != null) btnBack.setText(selectedLanguage.equals("ID") ? "KEMBALI" : "BACK");
        if (btnStart != null) btnStart.setText(selectedLanguage.equals("ID") ? "MULAI" : "START");
        if (btnStop != null) btnStop.setText(selectedLanguage.equals("ID") ? "BERHENTI" : "STOP");
        if (btnMenu != null) btnMenu.setText(selectedLanguage.equals("ID") ? "MENU" : "MENU");
        
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        resetToInitialState();

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnStart != null) btnStart.setOnClickListener(v -> checkPermissionAndStart());
        if (btnStop != null) btnStop.setOnClickListener(v -> stopTracking());
        if (btnMenu != null) btnMenu.setOnClickListener(v -> goToMenu());

        // FAB Coach AI
        FloatingActionButton fabCoachAI = findViewById(R.id.fabCoachAI);
        if (fabCoachAI != null) {
            fabCoachAI.setOnClickListener(v ->
                    startActivity(new Intent(RunningCountdownActivity.this, ChatbotActivity.class))
            );
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                stopTracking();
                finish();
            }
        });
    }

    private void checkPermissionAndStart() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1);
            } else {
                startTracking();
            }
        } else {
            startTracking();
        }
    }

    private void resetToInitialState() {
        stepCount = 0;
        tvCountdown.setText("00:00");
        if (tvLabel != null) tvLabel.setText(selectedLanguage.equals("ID") ? "Siap Lari?" : "Ready to Run?");
        if (tvSteps != null) tvSteps.setText("0");
        if (tvDistance != null) tvDistance.setText("0.00");
        circleBackground.clearAnimation();
        circleBackground.setBackgroundResource(R.drawable.circle_green);
        
        if (btnBack != null) {
            btnBack.setText(selectedLanguage.equals("ID") ? "KEMBALI" : "BACK");
            btnBack.setVisibility(View.VISIBLE);
        }
        if (btnStart != null) {
            btnStart.setText(selectedLanguage.equals("ID") ? "MULAI" : "START");
            btnStart.setVisibility(View.VISIBLE);
        }
        if (btnStop != null) {
            btnStop.setText(selectedLanguage.equals("ID") ? "BERHENTI" : "STOP");
            btnStop.setVisibility(View.GONE);
        }
        if (btnMenu != null) {
            btnMenu.setText(selectedLanguage.equals("ID") ? "MENU" : "MENU");
            btnMenu.setVisibility(View.GONE);
        }
        isTracking = false;
    }

    private void startTracking() {
        isTracking = true;
        startTime = SystemClock.uptimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
        
        if (btnBack != null) btnBack.setVisibility(View.GONE);
        if (btnStart != null) btnStart.setVisibility(View.GONE);
        if (btnStop != null) {
            btnStop.setText(selectedLanguage.equals("ID") ? "BERHENTI" : "STOP");
            btnStop.setVisibility(View.VISIBLE);
        }
        if (btnMenu != null) {
            btnMenu.setText(selectedLanguage.equals("ID") ? "MENU" : "MENU");
            btnMenu.setVisibility(View.VISIBLE);
        }
        
        if (tvLabel != null) tvLabel.setText(selectedLanguage.equals("ID") ? "Sedang Berlari..." : "Running...");

        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        } else if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, selectedLanguage.equals("ID") ? "Sensor tidak ditemukan!" : "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopTracking() {
        if (!isTracking) return;
        isTracking = false;
        timerHandler.removeCallbacks(timerRunnable);
        sensorManager.unregisterListener(this);
        showWorkoutFinishedDialog();
    }

    private void updateExtraInfo() {
        double distanceKm = stepCount * 0.00075;
        if (tvSteps != null) tvSteps.setText(String.valueOf(stepCount));
        if (tvDistance != null) tvDistance.setText(String.format("%.2f", distanceKm));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!isTracking) return;

        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            stepCount++;
            updateExtraInfo();
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            detectShakeStep(event);
        }
    }

    private void detectShakeStep(SensorEvent event) {
        long curTime = System.currentTimeMillis();
        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
                stepCount++;
                updateExtraInfo();
            }
            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private void resetWorkout() {
        stopTracking();
        resetToInitialState();
    }

    private void goToMenu() {
        stopTracking();
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

        btnDialogRestart.setOnClickListener(v -> { dialog.dismiss(); resetWorkout(); });
        btnDialogMenu   .setOnClickListener(v -> { dialog.dismiss(); goToMenu(); });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTracking();
    }
}