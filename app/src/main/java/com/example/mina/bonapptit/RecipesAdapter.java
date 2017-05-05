package com.example.mina.bonapptit;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mina.bonapptit.Data.Recipe;
import com.squareup.picasso.Picasso;

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
        @BindView(R.id.recipe_image)
        ImageView recipeImageView;

        public RecipesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            Recipe currentRecipe = mRecipes.get(position);
            recipeNameTextView.setText(currentRecipe.getmName());
            GradientDrawable errorDrawable = new GradientDrawable();
            errorDrawable.setShape(GradientDrawable.RECTANGLE);
            errorDrawable.setColor(Color.GRAY);
            Picasso.with(recipeImageView.getContext())
                    .load(Uri.parse(currentRecipe.getmImageUrl()))
                    .placeholder(errorDrawable)
                    .error(errorDrawable)
                    .into(recipeImageView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onClick(position);
        }
    }
}
