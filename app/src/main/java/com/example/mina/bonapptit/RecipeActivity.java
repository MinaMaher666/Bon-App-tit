package com.example.mina.bonapptit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.mina.bonapptit.Model.Ingredient;
import com.example.mina.bonapptit.Model.Recipe;
import com.example.mina.bonapptit.Model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {
    public static final String LOG_TAG = RecipeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        Intent sentIntent = getIntent();

        Recipe selectedRecipe = sentIntent.getParcelableExtra(MainFragment.SELECTED_RECIPE);
        List<Ingredient> ingredients = sentIntent.getParcelableArrayListExtra(MainFragment.SELECTED_RECIPE_INGREDIENTS);
        List<Step> steps = sentIntent.getParcelableArrayListExtra(MainFragment.SELECTED_RECIPE_STEPS);

        RecipeFragment recipeFragment = new RecipeFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_recipe_container, recipeFragment)
                .commit();

        recipeFragment.setIngredients(ingredients);
        recipeFragment.setSteps(steps);
    }
}
