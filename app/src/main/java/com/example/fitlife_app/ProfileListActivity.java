package com.example.fitlife_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ProfileListActivity extends AppCompatActivity {

    private RecyclerView rvProfiles;
    private Button btnAddProfile;
    private List<JSONObject> profileList;
    private ProfileListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        rvProfiles = findViewById(R.id.rvProfiles);
        btnAddProfile = findViewById(R.id.btnAddProfile);

        rvProfiles.setLayoutManager(new LinearLayoutManager(this));
        profileList = new ArrayList<>();
        adapter = new ProfileListAdapter(profileList);
        rvProfiles.setAdapter(adapter);

        btnAddProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfiles();
    }

    private void loadProfiles() {
        profileList.clear();
        SharedPreferences prefs = getSharedPreferences("FitLifeProfiles", MODE_PRIVATE);
        String profilesJson = prefs.getString("all_profiles", "[]");
        try {
            JSONArray jsonArray = new JSONArray(profilesJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                profileList.add(jsonArray.getJSONObject(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    private void selectProfile(JSONObject profile) {
        SharedPreferences prefs = getSharedPreferences("FitLifeProfile", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("name", profile.getString("name"));
            editor.putString("age", profile.getString("age"));
            editor.putString("weight", profile.getString("weight"));
            editor.putString("height", profile.getString("height"));
            editor.putString("goal", profile.getString("goal"));
            editor.apply();

            Intent intent = new Intent(this, ExerciseMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void editProfile(JSONObject profile) {
        try {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("edit_mode", true);
            intent.putExtra("original_name", profile.getString("name"));
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {
        private List<JSONObject> list;

        public ProfileListAdapter(List<JSONObject> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            try {
                JSONObject profile = list.get(position);
                holder.tvName.setText(profile.getString("name"));
                holder.tvGoal.setText(getString(R.string.target_prefix, profile.getString("goal")));
                holder.itemView.setOnClickListener(v -> selectProfile(profile));
                holder.btnEdit.setOnClickListener(v -> editProfile(profile));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvGoal;
            ImageButton btnEdit;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvProfileName);
                tvGoal = itemView.findViewById(R.id.tvProfileGoal);
                btnEdit = itemView.findViewById(R.id.btnEditProfile);
            }
        }
    }
}