package com.example.fitlife_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExerciseMenuActivity extends AppCompatActivity {

    private String selectedLanguage;
    private TextView tvTitle;
    private CardView cardRunning, cardPushup, cardYoga;
    private TextView tvRunningTitle, tvRunningDesc;
    private TextView tvPushupTitle, tvPushupDesc;
    private TextView tvYogaTitle, tvYogaDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_menu);

        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) selectedLanguage = "ID";

        tvTitle        = findViewById(R.id.tv_menu_title);
        cardRunning    = findViewById(R.id.card_running);
        cardPushup     = findViewById(R.id.card_pushup);
        cardYoga       = findViewById(R.id.card_yoga);
        tvRunningTitle = findViewById(R.id.tv_running_title);
        tvRunningDesc  = findViewById(R.id.tv_running_desc);
        tvPushupTitle  = findViewById(R.id.tv_pushup_title);
        tvPushupDesc   = findViewById(R.id.tv_pushup_desc);
        tvYogaTitle    = findViewById(R.id.tv_yoga_title);
        tvYogaDesc     = findViewById(R.id.tv_yoga_desc);

        setLanguage();
        setupCardListeners();

        // Back button
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // FAB Coach AI + animasi pulse
        FloatingActionButton fabCoachAI = findViewById(R.id.fabCoachAI);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        fabCoachAI.startAnimation(pulse);
        fabCoachAI.setOnClickListener(v -> {
            fabCoachAI.clearAnimation();
            startActivity(new Intent(ExerciseMenuActivity.this, ChatbotActivity.class));
        });
    }

    // Aktifkan pulse lagi saat kembali ke activity ini
    @Override
    protected void onResume() {
        super.onResume();
        FloatingActionButton fabCoachAI = findViewById(R.id.fabCoachAI);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        fabCoachAI.startAnimation(pulse);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FloatingActionButton fabCoachAI = findViewById(R.id.fabCoachAI);
        fabCoachAI.clearAnimation();
    }

    private void setLanguage() {
        if (selectedLanguage.equals("ID")) {
            tvTitle.setText("Menu Latihan");
            tvRunningTitle.setText("Berlari");
            tvRunningDesc.setText("Lacak jarak lari, waktu, dan kalori yang terbakar. Tetap termotivasi dengan informasi perkembangan dan target harian.");
            tvPushupTitle.setText("Push-up");
            tvPushupDesc.setText("Hitung push-up Anda, pantau setnya, dan pantau kalori yang terbakar. Bangun kekuatan tubuh bagian atas selangkah demi selangkah.");
            tvYogaTitle.setText("Yoga");
            tvYogaDesc.setText("Ikuti sesi yoga terpandu, tingkatkan fleksibilitas, dan rilekskan pikiran Anda dengan rutinitas yang menenangkan.");
        } else {
            tvTitle.setText("Exercise Menu");
            tvRunningTitle.setText("Running");
            tvRunningDesc.setText("Track your running distance, time, and calories burned. Stay motivated with progress updates and daily goals.");
            tvPushupTitle.setText("Push-up");
            tvPushupDesc.setText("Count your push-ups, monitor sets, and track calories burned. Build upper body strength step by step.");
            tvYogaTitle.setText("Yoga");
            tvYogaDesc.setText("Follow guided yoga sessions, improve flexibility, and relax your mind with calming routines.");
        }
    }

    private void setupCardListeners() {
        cardRunning.setOnClickListener(v -> {
            Intent intent = new Intent(ExerciseMenuActivity.this, RunningActivity.class);
            intent.putExtra("LANGUAGE", selectedLanguage);
            startActivity(intent);
        });
        cardPushup.setOnClickListener(v -> {
            Intent intent = new Intent(ExerciseMenuActivity.this, PushupActivity.class);
            intent.putExtra("LANGUAGE", selectedLanguage);
            startActivity(intent);
        });
        cardYoga.setOnClickListener(v -> {
            Intent intent = new Intent(ExerciseMenuActivity.this, YogaActivity.class);
            intent.putExtra("LANGUAGE", selectedLanguage);
            startActivity(intent);
        });
    }
}