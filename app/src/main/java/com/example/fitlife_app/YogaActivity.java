package com.example.fitlife_app;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class YogaActivity extends AppCompatActivity {

    private String selectedLanguage;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga);

        // Ambil bahasa yang dipilih
        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) {
            selectedLanguage = "ID";
        }

        tvTitle = findViewById(R.id.tv_yoga_title);

        // Set title berdasarkan bahasa
        if (selectedLanguage.equals("ID")) {
            tvTitle.setText("Yoga");
        } else {
            tvTitle.setText("Yoga");
        }
        // Handle back button dengan OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish(); // Kembali ke ExerciseMenuActivity
            }
        });
        // Tampilkan dialog instruksi saat activity dibuka
        showInstructionDialog();
    }

    private void showInstructionDialog() {
        // Buat dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yoga_instruction);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        // Inisialisasi views dalam dialog
        TextView tvDialogTitle = dialog.findViewById(R.id.tv_dialog_title);
        TextView tvInstruction = dialog.findViewById(R.id.tv_instruction);
        Button btnBack = dialog.findViewById(R.id.btn_back_dialog);
        Button btnStart = dialog.findViewById(R.id.btn_start_dialog);

        // Set text berdasarkan bahasa
        if (selectedLanguage.equals("ID")) {
            tvDialogTitle.setText("Instruksi Yoga");
            tvInstruction.setText(
                    "1. Temukan tempat yang tenang dan bentangkan matras Anda.\n\n" +
                            "2. Awali dengan bernapas dalam-dalam untuk memusatkan diri.\n\n" +
                            "3. Lakukan pose secara perlahan dan penuh perhatian.\n\n" +
                            "4. Tahan setiap pose selama 5-10 tarikan napas.\n\n" +
                            "5. Akhiri dengan pose relaksasi (Savasana)."
            );
            btnBack.setText("Kembali");
            btnStart.setText("Mulai");
        } else {
            tvDialogTitle.setText("Yoga Instruction");
            tvInstruction.setText(
                    "1. Find a quiet space and lay your mat\n\n" +
                            "2. Start with deep breathing to center yourself\n\n" +
                            "3. Move through poses slowly and mindfully\n\n" +
                            "4. Hold each pose for 5-10 breaths\n\n" +
                            "5. End with relaxation pose (Savasana)"
            );
            btnBack.setText("Back");
            btnStart.setText("Start");
        }

        // Tombol Kembali/Back - kembali ke ExerciseMenu
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish(); // Kembali ke ExerciseMenuActivity
            }
        });

        // Tombol Mulai/Start - mulai yoga session
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // TODO: Mulai yoga session
                startYogaSession();
            }
        });

        dialog.show();
    }

    private void startYogaSession() {
        // Pindah ke YogaCountdownActivity
        Intent intent = new Intent(YogaActivity.this, YogaCountdownActivity.class);
        intent.putExtra("LANGUAGE", selectedLanguage);
        startActivity(intent);
        finish();
    }

//    @Override
//    public void onBackPressed() {
//        // Kembali ke ExerciseMenuActivity
//        super.onBackPressed();
//        finish();
//    }
}