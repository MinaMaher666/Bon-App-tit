package com.example.mina.bonapptit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mina.bonapptit.Model.Step;

public class StepActivity extends AppCompatActivity {
    StepFragment stepFragment;

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

        Intent sentIntent = getIntent();
        Step sentStep = sentIntent.getParcelableExtra(RecipeFragment.SELECTED_STEP);
        String stepDescription = sentStep.getmDescription();
        stepFragment.setDescription(stepDescription);
    }
}
