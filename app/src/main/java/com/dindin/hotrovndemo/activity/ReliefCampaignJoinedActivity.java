package com.dindin.hotrovndemo.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dindin.hotrovndemo.api.param.response.News;
import com.dindin.hotrovndemo.R;
import com.dindin.hotrovndemo.databinding.ActivityReliefCampaignJoinedBinding;
import com.dindin.hotrovndemo.fragment.ShowListReliefFragment;

import java.util.ArrayList;
import java.util.List;

public class ReliefCampaignJoinedActivity extends AppCompatActivity {
    ActivityReliefCampaignJoinedBinding binding;
    Intent intent;
    int key;
    String phoneNumber;
    int field;
    List<News> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_relief_campaign_joined);
        intent = getIntent();
        key = intent.getIntExtra("key", 0);
        phoneNumber = intent.getStringExtra("phone");
        field = intent.getIntExtra("field", 0);
        createList();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentListReliefCampaignJoined, new ShowListReliefFragment(news, key, field, phoneNumber))
                .commit();
        binding.btnBack.setOnClickListener(v -> finish());
    }
    private void getListHelpJobsByPhone() {

    }
    private void createList() {
        news.add(new News());
        news.add(new News());
        news.add(new News());
        news.add(new News());
        news.add(new News());
        news.add(new News());
    }
}