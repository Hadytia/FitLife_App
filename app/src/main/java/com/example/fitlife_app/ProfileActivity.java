package com.example.fitlife_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etAge, etWeight, etHeight;
    private Spinner spinnerGoal;
    private Button btnSave;
    private boolean isEditMode = false;
    private String originalName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        spinnerGoal = findViewById(R.id.spinnerGoal);
        btnSave = findViewById(R.id.btnSaveProfile);

        isEditMode = getIntent().getBooleanExtra("edit_mode", false);
        originalName = getIntent().getStringExtra("original_name");

        if (isEditMode) {
            loadProfileForEditing(originalName);
            btnSave.setText(R.string.update_profile);
        }

        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void loadProfileForEditing(String name) {
        SharedPreferences prefs = getSharedPreferences("FitLifeProfiles", MODE_PRIVATE);
        String profilesJson = prefs.getString("all_profiles", "[]");
        try {
            JSONArray jsonArray = new JSONArray(profilesJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject profile = jsonArray.getJSONObject(i);
                if (profile.getString("name").equals(name)) {
                    etName.setText(profile.getString("name"));
                    etAge.setText(profile.getString("age"));
                    etWeight.setText(profile.getString("weight"));
                    etHeight.setText(profile.getString("height"));
                    // Spinner goal setting could be added here
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveProfile() {
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String height = etHeight.getText().toString().trim();
        String goal = spinnerGoal.getSelectedItem().toString();

        if (name.isEmpty() || age.isEmpty() || weight.isEmpty() || height.isEmpty()) {
            Toast.makeText(this, R.string.fill_all_data, Toast.LENGTH_SHORT).show();
            return;
        }

        saveToAllProfiles(name, age, weight, height, goal);

        // Set as current active profile
        SharedPreferences activePrefs = getSharedPreferences("FitLifeProfile", MODE_PRIVATE);
        SharedPreferences.Editor activeEditor = activePrefs.edit();
        activeEditor.putString("name", name);
        activeEditor.putString("age", age);
        activeEditor.putString("weight", weight);
        activeEditor.putString("height", height);
        activeEditor.putString("goal", goal);
        activeEditor.apply();

        Toast.makeText(this, R.string.profile_saved, Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(ProfileActivity.this, ExerciseMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void saveToAllProfiles(String name, String age, String weight, String height, String goal) {
        SharedPreferences prefs = getSharedPreferences("FitLifeProfiles", MODE_PRIVATE);
        String profilesJson = prefs.getString("all_profiles", "[]");
        try {
            JSONArray jsonArray = new JSONArray(profilesJson);
            JSONObject newProfile = new JSONObject();
            newProfile.put("name", name);
            newProfile.put("age", age);
            newProfile.put("weight", weight);
            newProfile.put("height", height);
            newProfile.put("goal", goal);

            if (isEditMode) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.getJSONObject(i).getString("name").equals(originalName)) {
                        jsonArray.put(i, newProfile);
                        break;
                    }
                }
            } else {
                jsonArray.put(newProfile);
            }

            prefs.edit().putString("all_profiles", jsonArray.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}