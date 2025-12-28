package com.example.fitlife_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private String selectedLanguage = "ID"; // Default bahasa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Inisialisasi view
        ImageView flagID = findViewById(R.id.flag_id);
        ImageView flagEN = findViewById(R.id.flag_en);
        LinearLayout langID = findViewById(R.id.lang_id);
        LinearLayout langEN = findViewById(R.id.lang_en);
        TextView textID = findViewById(R.id.text_id);
        TextView textEN = findViewById(R.id.text_en);
        Button btnContinue = findViewById(R.id.btn_continue);

        // Set listener untuk pilihan bahasa Indonesia
        langID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLanguage = "ID";
                // Efek visual untuk yang dipilih
                flagID.setAlpha(1.0f);
                flagEN.setAlpha(0.5f);
                textID.setAlpha(1.0f);
                textEN.setAlpha(0.5f);
            }
        });

        // Set listener untuk pilihan bahasa English
        langEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedLanguage = "EN";
                // Efek visual untuk yang dipilih
                flagEN.setAlpha(1.0f);
                flagID.setAlpha(0.5f);
                textEN.setAlpha(1.0f);
                textID.setAlpha(0.5f);
            }
        });

        // Tombol Continue untuk pindah ke MainActivity
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                intent.putExtra("LANGUAGE", selectedLanguage);
                startActivity(intent);
                finish();
            }
        });
    }
}