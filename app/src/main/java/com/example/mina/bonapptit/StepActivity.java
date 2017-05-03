package com.example.mina.bonapptit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mina.bonapptit.Model.Step;

public class StepActivity extends AppCompatActivity {
    StepFragment stepFragment;
    public static final String LOG_TAG = StepActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        stepFragment = new StepFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.step_fragment_container, stepFragment)
                .commitNow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendDataToFragment();
    }

    private void sendDataToFragment() {
        Intent sentIntent = getIntent();
        Step sentStep = sentIntent.getParcelableExtra(RecipeFragment.SELECTED_STEP);
        String stepDescription = sentStep.getmDescription();
        String imageUri = sentStep.getmThumbnailUrl();
        String videoUri = sentStep.getmVideoUrl();
        stepFragment.setDescription(stepDescription);
        stepFragment.setImageView(imageUri);
        stepFragment.setVideoView(videoUri);
    }
}
