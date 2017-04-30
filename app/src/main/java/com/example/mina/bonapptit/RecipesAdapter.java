package com.example.mina.bonapptit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mina.bonapptit.Model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipesAdapter  extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>{
    private List<Recipe> mRecipes;
    private RecipeOnClickListener mOnClickListener;

    public interface RecipeOnClickListener {
        void onClick(int position);
    }

    public RecipesAdapter(List<Recipe> recipes, RecipeOnClickListener clickListener) {
        this.mRecipes = recipes;
        this.mOnClickListener = clickListener;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.recipe_name)
        TextView recipeNameTextView;

        public RecipesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            Recipe currentRecipe = mRecipes.get(position);
            recipeNameTextView.setText(currentRecipe.getmName());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onClick(position);
        }
    }
}
