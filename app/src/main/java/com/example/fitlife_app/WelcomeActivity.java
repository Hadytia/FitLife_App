package com.example.fitlife_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private String selectedLanguage;
    private TextView tvWelcome;
    private TextView tvQuestion;
    private Button btnKeluar;
    private Button btnMulai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Ambil bahasa yang dipilih dari SplashActivity
        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) {
            selectedLanguage = "ID"; // Default
        }

        // Inisialisasi views
        tvWelcome = findViewById(R.id.tv_welcome);
        tvQuestion = findViewById(R.id.tv_question);
        btnKeluar = findViewById(R.id.btn_keluar);
        btnMulai = findViewById(R.id.btn_mulai);

        // Set text berdasarkan bahasa
        setLanguage();

        // Listener untuk tombol Keluar
        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Keluar dari aplikasi
                finishAffinity();
            }
        });

        // Listener untuk tombol Mulai
        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke ExerciseMenuActivity
                Intent intent = new Intent(WelcomeActivity.this, ExerciseMenuActivity.class);
                intent.putExtra("LANGUAGE", selectedLanguage);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setLanguage() {
        if (selectedLanguage.equals("ID")) {
            // Bahasa Indonesia
            tvWelcome.setText("Selamat Datang Hadytia,");
            tvQuestion.setText("Apakah Anda siap\nuntuk latihan?");
            btnKeluar.setText("KELUAR");
            btnMulai.setText("MULAI");
        } else {
            // Bahasa Inggris
            tvWelcome.setText("Welcome Hadytia,");
            tvQuestion.setText("Are you ready for\nyour excercise?");
            btnKeluar.setText("EXIT");
            btnMulai.setText("START");
        }
    }
}