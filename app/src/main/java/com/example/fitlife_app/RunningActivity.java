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

public class RunningActivity extends AppCompatActivity {

    private String selectedLanguage;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        // Ambil bahasa yang dipilih
        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) {
            selectedLanguage = "ID";
        }

        tvTitle = findViewById(R.id.tv_running_title);

        // Set title berdasarkan bahasa
        if (selectedLanguage.equals("ID")) {
            tvTitle.setText("Berlari");
        } else {
            tvTitle.setText("Running");
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
        dialog.setContentView(R.layout.dialog_running_instruction);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        // Inisialisasi views dalam dialog
        TextView tvDialogTitle = dialog.findViewById(R.id.tv_dialog_title);
        TextView tvInstruction = dialog.findViewById(R.id.tv_instruction);
        Button btnBack = dialog.findViewById(R.id.btn_back_dialog);
        Button btnStart = dialog.findViewById(R.id.btn_start_dialog);

        // Set text berdasarkan bahasa
        if (selectedLanguage.equals("ID")) {
            tvDialogTitle.setText("Instruksi Berlari");
            tvInstruction.setText(
                    "1. Awali dengan pemanasan berjalan kaki selama 2-3 menit.\n\n" +
                            "2. Jaga postur tubuh tetap tegak dan bahu rileks.\n\n" +
                            "3.Endaratlah dengan posisi setengah kaki dan dorong dengan jari-jari kaki.\n\n" +
                            "4. Ayunkan lengan 90 derajat dan bernapaslah dengan teratur.\n\n" +
                            "5. Pendinginan dengan berjalan kaki perlahan."
            );
            btnBack.setText("Kembali");
            btnStart.setText("Mulai");
        } else {
            tvDialogTitle.setText("Running Instruction");
            tvInstruction.setText(
                    "1. Start with a warm-up walk for 2-3 minutes\n\n" +
                            "2. Keep your posture upright and shoulders relaxed\n\n" +
                            "3. Land on mid-foot and push off with your toes\n\n" +
                            "4. Swing arms at 90 degrees and breathe steadily\n\n" +
                            "5. Cool down with a slow walk"
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

        // Tombol Mulai/Start - mulai tracking running
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // TODO: Mulai tracking running
                startRunningTracking();
            }
        });

        dialog.show();
    }

    private void startRunningTracking() {
        // Pindah ke RunningCountdownActivity
        Intent intent = new Intent(RunningActivity.this, RunningCountdownActivity.class);
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