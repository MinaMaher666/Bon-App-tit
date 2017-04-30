package com.example.mina.bonapptit;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mina.bonapptit.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainFragment extends Fragment implements RecipesAdapter.RecipeOnClickListener{
    @BindView(R.id.recipes_recycler_view)
    RecyclerView recipesRecyclerView;
    @BindView(R.id.empty_recipes_tv)
    View emptyListView;

    private List<Recipe> mRecipes;
    RecipesAdapter recipesAdapter;

    public static final String LOG_TAG = MainFragment.class.getSimpleName();

    public static final String SELECTED_RECIPE = "selected_recipe";
    public static final String SELECTED_RECIPE_INGREDIENTS = "selected_ingredients";
    public static final String SELECTED_RECIPE_STEPS = "selected_steps";

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        mRecipes = new ArrayList<>();


        if (getResources().getBoolean(R.bool.isTablet)) {
            GridLayoutManager layoutManager;
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutManager = new GridLayoutManager(getContext(), 3);
            } else {
                layoutManager = new GridLayoutManager(getContext(), 2);
            }
            recipesRecyclerView.setLayoutManager(layoutManager);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recipesRecyclerView.setLayoutManager(layoutManager);
        }
        recipesAdapter = new RecipesAdapter(mRecipes, this);
        recipesRecyclerView.setAdapter(recipesAdapter);

        return rootView;
    }


    public void setRecipes(List<Recipe> recipes) {
        mRecipes.addAll(recipes);
        recipesAdapter.notifyDataSetChanged();

        if (mRecipes.isEmpty()) {
            showEmptyListView();
        } else {
            hideEmptyListView();
        }
    }

    public void showEmptyListView() {
        recipesRecyclerView.setVisibility(View.GONE);
        emptyListView.setVisibility(View.VISIBLE);
    }

    public void hideEmptyListView() {
        emptyListView.setVisibility(View.GONE);
        recipesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int position) {
        Recipe selectedRecipe = mRecipes.get(position);
        Intent detailIntent = new Intent(getContext(), RecipeActivity.class);
        detailIntent.putExtra(SELECTED_RECIPE, selectedRecipe);
        detailIntent.putParcelableArrayListExtra(SELECTED_RECIPE_INGREDIENTS, (ArrayList<? extends Parcelable>) selectedRecipe.getmIngredients());
        detailIntent.putParcelableArrayListExtra(SELECTED_RECIPE_STEPS, (ArrayList<? extends Parcelable>) selectedRecipe.getmSteps());

        startActivity(detailIntent);
    }
}
