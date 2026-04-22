package com.example.fitlife_app;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PushupActivity extends AppCompatActivity {

    private String selectedLanguage;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup);

        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) selectedLanguage = "ID";

        tvTitle = findViewById(R.id.tv_pushup_title);
        tvTitle.setText("Push-Up");

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        // FAB Coach AI
        FloatingActionButton fabCoachAI = findViewById(R.id.fabCoachAI);
        fabCoachAI.setOnClickListener(v -> {
            Intent intent = new Intent(PushupActivity.this, ChatbotActivity.class);
            startActivity(intent);
        });

        showInstructionDialog();
    }

    private void showInstructionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pushup_instruction);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView tvDialogTitle = dialog.findViewById(R.id.tv_dialog_title);
        TextView tvInstruction = dialog.findViewById(R.id.tv_instruction);
        Button btnBack         = dialog.findViewById(R.id.btn_back_dialog);
        Button btnStart        = dialog.findViewById(R.id.btn_start_dialog);

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

        btnBack.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        btnStart.setOnClickListener(v -> {
            dialog.dismiss();
            startPushupCounting();
        });

        dialog.show();
    }

    private void startPushupCounting() {
        Intent intent = new Intent(PushupActivity.this, PushupCountdownActivity.class);
        intent.putExtra("LANGUAGE", selectedLanguage);
        startActivity(intent);
        finish();
    }
}