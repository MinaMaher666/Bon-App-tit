package com.example.mina.bonapptit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mina.bonapptit.Model.Ingredient;
import com.example.mina.bonapptit.Model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment implements StepsAdapter.StepOnClickListener{
    @BindView(R.id.ingredients_recycler_view)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.steps_recycler_view)
    RecyclerView stepsRecyclerView;

    public static String SELECTED_STEP = "selected_step";

    private List<Ingredient> mIngredients;
    private List<Step> mSteps;

    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;

    public RecipeFragment(){
        mIngredients = new ArrayList<>();
        mIngredientsAdapter = new IngredientsAdapter(mIngredients);

        mSteps = new ArrayList<>();
        mStepsAdapter = new StepsAdapter(mSteps, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientsRecyclerView.setAdapter(mIngredientsAdapter);

        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(getContext());
        stepsRecyclerView.setLayoutManager(stepsLayoutManager);
        stepsRecyclerView.setAdapter(mStepsAdapter);

        return rootView;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients.addAll(ingredients);
        mIngredientsAdapter.notifyDataSetChanged();
    }

    public void setSteps(List<Step> steps) {
        mSteps.addAll(steps);
        mStepsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        Step selectedStep = mSteps.get(position);
        Intent stepIntent = new Intent(getContext(), StepActivity.class);
        stepIntent.putExtra(SELECTED_STEP, selectedStep);

        startActivity(stepIntent);
    }
}