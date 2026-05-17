package com.example.fitlife_app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class YogaCountdownActivity extends AppCompatActivity {

    private String selectedLanguage;
    private TextView tvTitle, tvCountdown, tvPoseName, tvPoseProgress, tvBreathing;
    private ImageView ivPoseSilhouette;
    private View circleBackground;
    private Button btnBack, btnStart, btnRestart, btnMenu;
    private CountDownTimer poseTimer;
    private boolean isFlowRunning = false;
    
    private int currentPoseIndex = 0;
    private final int POSE_DURATION = 20000; // 20 detik per pose
    
    private int[] poseNames = {
        R.string.yoga_pose_1,
        R.string.yoga_pose_2,
        R.string.yoga_pose_3,
        R.string.yoga_pose_4
    };

    private int[] poseImages = {
        R.drawable.pose_mountain,
        R.drawable.pose_child,
        R.drawable.pose_cobra,
        R.drawable.pose_savasana
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_countdown);

        selectedLanguage = getIntent().getStringExtra("LANGUAGE");
        if (selectedLanguage == null) selectedLanguage = "ID";

        tvTitle          = findViewById(R.id.tv_countdown_title);
        tvCountdown      = findViewById(R.id.tv_countdown);
        tvPoseName       = findViewById(R.id.tv_pose_name);
        tvPoseProgress   = findViewById(R.id.tv_pose_progress);
        tvBreathing      = findViewById(R.id.tv_breathing);
        ivPoseSilhouette = findViewById(R.id.iv_pose_silhouette);
        circleBackground = findViewById(R.id.circle_background);
        btnBack          = findViewById(R.id.btn_back);
        btnStart         = findViewById(R.id.btn_start);
        btnRestart       = findViewById(R.id.btn_restart);
        btnMenu          = findViewById(R.id.btn_menu);

        tvTitle.setText("Yoga");
        
        // Set Button Text based on Language
        if (btnBack != null) btnBack.setText(selectedLanguage.equals("ID") ? "KEMBALI" : "BACK");
        if (btnStart != null) btnStart.setText(selectedLanguage.equals("ID") ? "MULAI" : "START");
        if (btnRestart != null) btnRestart.setText(selectedLanguage.equals("ID") ? "MULAI ULANG" : "RESTART");
        if (btnMenu != null) btnMenu.setText(selectedLanguage.equals("ID") ? "MENU" : "MENU");

        resetToInitialState();

        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        if (btnStart != null) btnStart.setOnClickListener(v -> startYogaFlow());
        if (btnRestart != null) btnRestart.setOnClickListener(v -> resetFlow());
        if (btnMenu != null) btnMenu.setOnClickListener(v -> goToMenu());

        // FAB Coach AI
        FloatingActionButton fabCoachAI = findViewById(R.id.fabCoachAI);
        if (fabCoachAI != null) {
            fabCoachAI.setOnClickListener(v ->
                    startActivity(new Intent(YogaCountdownActivity.this, ChatbotActivity.class))
            );
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                stopFlow();
                finish();
            }
        });
    }

    private void resetToInitialState() {
        currentPoseIndex = 0;
        tvCountdown.setText("00");
        if (tvPoseName != null) tvPoseName.setText(selectedLanguage.equals("ID") ? "Siap Mulai?" : "Ready?");
        if (tvPoseProgress != null) tvPoseProgress.setText(selectedLanguage.equals("ID") ? "Sesi Yoga Terpandu" : "Guided Yoga Session");
        if (tvBreathing != null) tvBreathing.setText("");
        if (ivPoseSilhouette != null) ivPoseSilhouette.setImageResource(R.drawable.pose_mountain);
        if (circleBackground != null) {
            circleBackground.clearAnimation();
            circleBackground.setBackgroundResource(R.drawable.circle_green);
        }
        
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
        isFlowRunning = false;
    }

    private void startYogaFlow() {
        isFlowRunning = true;
        if (btnBack != null) btnBack.setVisibility(View.GONE);
        if (btnStart != null) btnStart.setVisibility(View.GONE);
        if (btnRestart != null) btnRestart.setVisibility(View.VISIBLE);
        if (btnMenu != null) btnMenu.setVisibility(View.VISIBLE);
        
        startPose(0);
        startBreathingAnimation();
    }

    private void startPose(int index) {
        if (index >= poseNames.length) {
            stopFlow();
            showWorkoutFinishedDialog();
            return;
        }

        currentPoseIndex = index;
        if (tvPoseName != null) tvPoseName.setText(getString(poseNames[index]));
        if (tvPoseProgress != null) tvPoseProgress.setText(getString(R.string.pose_progress, index + 1, poseNames.length));
        
        if (ivPoseSilhouette != null) {
            ivPoseSilhouette.setImageResource(poseImages[index]);
        }

        vibrateEffect();

        if (poseTimer != null) poseTimer.cancel();
        
        poseTimer = new CountDownTimer(POSE_DURATION, 1000) {
            @Override
            public void onTick(long ms) {
                tvCountdown.setText(String.format("%02d", ms / 1000));
            }
            @Override public void onFinish() {
                startPose(currentPoseIndex + 1);
            }
        }.start();
    }

    private void startBreathingAnimation() {
        if (circleBackground == null) return;

        ScaleAnimation anim = new ScaleAnimation(
                1.0f, 1.4f, 1.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        anim.setDuration(4000); 
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {
                if (tvBreathing != null) {
                    if (tvBreathing.getText().equals(getString(R.string.inhale))) {
                        tvBreathing.setText(R.string.exhale);
                    } else {
                        tvBreathing.setText(R.string.inhale);
                    }
                }
            }
            @Override public void onAnimationEnd(Animation animation) {}
        });
        
        if (tvBreathing != null) tvBreathing.setText(R.string.inhale);
        circleBackground.startAnimation(anim);
    }

    private void stopFlow() {
        isFlowRunning = false;
        if (poseTimer != null) poseTimer.cancel();
        if (circleBackground != null) circleBackground.clearAnimation();
        if (tvBreathing != null) tvBreathing.setText("");
    }

    private void resetFlow() {
        stopFlow();
        resetToInitialState();
    }

    private void vibrateEffect() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            v.vibrate(200);
        }
    }

    private void goToMenu() {
        stopFlow();
        Intent intent = new Intent(this, ExerciseMenuActivity.class);
        intent.putExtra("LANGUAGE", selectedLanguage);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void showWorkoutFinishedDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yoga_finished);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView tvMessage      = dialog.findViewById(R.id.tv_finished_message);
        Button btnDialogRestart = dialog.findViewById(R.id.btn_dialog_restart);
        Button btnDialogMenu    = dialog.findViewById(R.id.btn_dialog_menu);

        if (selectedLanguage.equals("ID")) {
            tvMessage.setText("Latihan selesai!\n\nSelamat! Anda menyelesaikan sesi yoga dengan penuh kesadaran dan keseimbangan.");
            btnDialogRestart.setText("Mulai Ulang");
            btnDialogMenu.setText("Menu");
        } else {
            tvMessage.setText("Workout finished!\n\nCongratulations! You finished your yoga session with mindfulness and balance.");
            btnDialogRestart.setText("Restart");
            btnDialogMenu.setText("Menu");
        }

        btnDialogRestart.setOnClickListener(v -> { dialog.dismiss(); resetFlow(); });
        btnDialogMenu   .setOnClickListener(v -> { dialog.dismiss(); goToMenu(); });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopFlow();
    }
}