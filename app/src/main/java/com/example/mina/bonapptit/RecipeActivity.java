package com.example.mina.bonapptit;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;

import com.example.mina.bonapptit.Data.Ingredient;
import com.example.mina.bonapptit.Data.Recipe;
import com.example.mina.bonapptit.Data.RecipesContentProvider;
import com.example.mina.bonapptit.Data.RecipesContract;
import com.example.mina.bonapptit.Data.Step;
import com.example.mina.bonapptit.widget.BakingWidget;

import java.util.List;

import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {
    public static final String LOG_TAG = RecipeActivity.class.getSimpleName();
    private StepFragment stepFragment;
    private List<Step> steps;
    private boolean mIsInWidget;
    private Recipe mCurrentRecipe;
    private List<Ingredient> mIngredients;
    private Menu mRecipeMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        final Intent sentIntent = getIntent();

        mCurrentRecipe = sentIntent.getParcelableExtra(MainFragment.SELECTED_RECIPE);

        String selection = RecipesContract.IngredientEntry.RECIPE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(mCurrentRecipe.getmId())};
        Cursor cursor = getContentResolver().query(RecipesContentProvider.Ingredients.INGREDIENTS,
                null, selection, selectionArgs, null);

        if (cursor != null && cursor.getCount() > 0) {
            mIsInWidget = true;
        } else {
            mIsInWidget = false;
        } if (cursor != null) cursor.close();

        mIngredients = sentIntent.getParcelableArrayListExtra(MainFragment.SELECTED_RECIPE_INGREDIENTS);
        steps = sentIntent.getParcelableArrayListExtra(MainFragment.SELECTED_RECIPE_STEPS);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipeTitle(mCurrentRecipe.getmName());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_recipe_container, recipeFragment)
                .commit();

        recipeFragment.setIngredients(mIngredients);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipe, menu);
        mRecipeMenu = menu;

        updateMenuIcon();

        return true;
    }

    private void updateMenuIcon() {
        if (mIsInWidget) {
            mRecipeMenu.getItem(0).setIcon(ContextCompat.getDrawable(RecipeActivity.this, R.drawable.ic_action_remove_from_widget));
        } else {
            mRecipeMenu.getItem(0).setIcon(ContextCompat.getDrawable(RecipeActivity.this, R.drawable.ic_action_add_to_widget));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_add_to_widget:
                changeWidgetIngredients();
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeWidgetIngredients() {
        getContentResolver().delete(RecipesContentProvider.Ingredients.INGREDIENTS, null, null);

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(this, BakingWidget.class));
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.baking_widget);

        if (!mIsInWidget) {
            insertIntoProvider();
            remoteViews.setViewVisibility(R.id.widget_recipe_title, View.VISIBLE);
            remoteViews.setTextViewText(R.id.widget_recipe_title, mCurrentRecipe.getmName());
            mIsInWidget = true;
        } else {
            remoteViews.setViewVisibility(R.id.widget_recipe_title, View.GONE);
            mIsInWidget = false;
        }

        updateMenuIcon();

        widgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_list_view);
        widgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    private void insertIntoProvider() {
        ContentValues[] cvs = new ContentValues[mIngredients.size()];
        for (int i=0 ; i< mIngredients.size() ; i++) {
            cvs[i] = new ContentValues();
            cvs[i].put(RecipesContract.IngredientEntry.RECIPE_ID, mCurrentRecipe.getmId());
            cvs[i].put(RecipesContract.IngredientEntry.RECIPE_NAME, mCurrentRecipe.getmName());
            cvs[i].put(RecipesContract.IngredientEntry.INGREDIENT_NAME, mIngredients.get(i).getmIngredient());
            cvs[i].put(RecipesContract.IngredientEntry.MEASURE, mIngredients.get(i).getmMeasure());
            cvs[i].put(RecipesContract.IngredientEntry.QUANTITY, mIngredients.get(i).getmQuantity());
        }
        getContentResolver().bulkInsert(RecipesContentProvider.Ingredients.INGREDIENTS, cvs);
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
