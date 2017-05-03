package com.example.mina.bonapptit;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.mina.bonapptit.Model.Ingredient;
import com.example.mina.bonapptit.Model.Recipe;
import com.example.mina.bonapptit.Model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {
    public static final String LOG_TAG = RecipeActivity.class.getSimpleName();
    private StepFragment stepFragment;
    private List<Step> steps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        final Intent sentIntent = getIntent();

        List<Ingredient> ingredients = sentIntent.getParcelableArrayListExtra(MainFragment.SELECTED_RECIPE_INGREDIENTS);
        steps = sentIntent.getParcelableArrayListExtra(MainFragment.SELECTED_RECIPE_STEPS);

        RecipeFragment recipeFragment = new RecipeFragment();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_recipe_container, recipeFragment)
                .commit();

        recipeFragment.setIngredients(ingredients);
        recipeFragment.setSteps(steps);

        View tabletView = findViewById(R.id.tablet_fragment_step_container);
        if (tabletView != null) {
            stepFragment = new StepFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tablet_fragment_step_container, stepFragment)
                    .commit();

            recipeFragment.setUpdateFragment(new RecipeFragment.UpdateStepFragment() {
                @Override
                public void update(int position) {
                    if (stepFragment != null) {
                        sendData(position);
                    }
                }
            });
        }
    }

    private void sendData(int position) {
        Step selectedStep = steps.get(position);

        String stepDescription = selectedStep.getmDescription();
        String imageUri = selectedStep.getmThumbnailUrl();
        String videoUri = selectedStep.getmVideoUrl();
        stepFragment.setDescription(stepDescription);
        stepFragment.setImageView(imageUri);
        stepFragment.setVideoView(videoUri);
    }
}
