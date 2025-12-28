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

public class PushupActivity extends AppCompatActivity {

    private String selectedLanguage;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup);

        // Ambil bahasa yang dipilih
        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) {
            selectedLanguage = "ID";
        }

        tvTitle = findViewById(R.id.tv_pushup_title);

        // Set title berdasarkan bahasa
        if (selectedLanguage.equals("ID")) {
            tvTitle.setText("Push-Up");
        } else {
            tvTitle.setText("Push-Up");
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
        dialog.setContentView(R.layout.dialog_pushup_instruction);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        // Inisialisasi views dalam dialog
        TextView tvDialogTitle = dialog.findViewById(R.id.tv_dialog_title);
        TextView tvInstruction = dialog.findViewById(R.id.tv_instruction);
        Button btnBack = dialog.findViewById(R.id.btn_back_dialog);
        Button btnStart = dialog.findViewById(R.id.btn_start_dialog);

        // Set text berdasarkan bahasa
        if (selectedLanguage.equals("ID")) {
            tvDialogTitle.setText("Instruksi Push Up");
            tvInstruction.setText(
                    "1. Mulailah dengan posisi plank\n\n" +
                            "2. Turunkan dada ke lantai\n\n" +
                            "3. Dorong kembali ke atas\n\n" +
                            "4. Ulangi"
            );
            btnBack.setText("Kembali");
            btnStart.setText("Mulai");
        } else {
            tvDialogTitle.setText("Push-Up Instruction");
            tvInstruction.setText(
                    "1. Start in a plank position\n\n" +
                            "2. Lower your chest towards the floor\n\n" +
                            "3. Push back up\n\n" +
                            "4. Repeat"
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

        // Tombol Mulai/Start - mulai counting push-up
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // TODO: Mulai counting push-up
                startPushupCounting();
            }
        });

        dialog.show();
    }

    private void startPushupCounting() {
        // Pindah ke PushupCountdownActivity
        Intent intent = new Intent(PushupActivity.this, PushupCountdownActivity.class);
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