package com.example.fitlife_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ExerciseMenuActivity extends AppCompatActivity {

    private String selectedLanguage;
    private TextView tvTitle;
    private CardView cardRunning, cardPushup, cardYoga;
    private TextView tvRunningTitle, tvRunningDesc;
    private TextView tvPushupTitle, tvPushupDesc;
    private TextView tvYogaTitle, tvYogaDesc;
    private ImageView btnBack, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_menu);

        // Ambil bahasa yang dipilih
        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) {
            selectedLanguage = "ID";
        }

        // Inisialisasi views
        tvTitle = findViewById(R.id.tv_menu_title);

        cardRunning = findViewById(R.id.card_running);
        cardPushup = findViewById(R.id.card_pushup);
        cardYoga = findViewById(R.id.card_yoga);

        tvRunningTitle = findViewById(R.id.tv_running_title);
        tvRunningDesc = findViewById(R.id.tv_running_desc);
        tvPushupTitle = findViewById(R.id.tv_pushup_title);
        tvPushupDesc = findViewById(R.id.tv_pushup_desc);
        tvYogaTitle = findViewById(R.id.tv_yoga_title);
        tvYogaDesc = findViewById(R.id.tv_yoga_desc);

        btnBack = findViewById(R.id.btn_back);
        btnNext = findViewById(R.id.btn_next);

        // Set bahasa
        setLanguage();

        // Set click listeners untuk setiap card
        setupCardListeners();

        // Navigation buttons
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kembali ke Welcome screen
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke screen berikutnya (misalnya Dashboard)
                Intent intent = new Intent(ExerciseMenuActivity.this, MainActivity.class);
                intent.putExtra("LANGUAGE", selectedLanguage);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setLanguage() {
        if (selectedLanguage.equals("ID")) {
            // Bahasa Indonesia
            tvTitle.setText("Menu Latihan");

            tvRunningTitle.setText("Berlari");
            tvRunningDesc.setText("Lacak jarak lari, waktu, dan kalori yang terbakar. Tetap termotivasi dengan informasi perkembangan dan target harian.");

            tvPushupTitle.setText("Push-up");
            tvPushupDesc.setText("Hitung push-up Anda, pantau setnya, dan pantau kalori yang terbakar. Bangun kekuatan tubuh bagian atas selangkah demi selangkah.");

            tvYogaTitle.setText("Yoga");
            tvYogaDesc.setText("Ikuti sesi yoga terpandu, tingkatkan fleksibilitas, dan rilekskan pikiran Anda dengan rutinitas yang menenangkan.");

        } else {
            // Bahasa Inggris
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
        // Running Card
        cardRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke Running Activity
                Intent intent = new Intent(ExerciseMenuActivity.this, RunningActivity.class);
                intent.putExtra("LANGUAGE", selectedLanguage);
                startActivity(intent);
            }
        });

        // Push-up Card
        cardPushup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke Push-up Activity
                Intent intent = new Intent(ExerciseMenuActivity.this, PushupActivity.class);
                intent.putExtra("LANGUAGE", selectedLanguage);
                startActivity(intent);
            }
        });

        // Yoga Card
        cardYoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke Yoga Activity
                Intent intent = new Intent(ExerciseMenuActivity.this, YogaActivity.class);
                intent.putExtra("LANGUAGE", selectedLanguage);
                startActivity(intent);
            }
        });
    }
}