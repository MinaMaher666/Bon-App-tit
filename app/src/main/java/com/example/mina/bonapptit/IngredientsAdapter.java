package com.example.mina.bonapptit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mina.bonapptit.Data.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>{
    private List<Ingredient> mIngredients;

    public IngredientsAdapter(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingreditent_text_view)
        TextView ingredientTextView;
        @BindView(R.id.quantity_text_view)
        TextView quantityTextView;
        @BindView(R.id.measure_text_view)
        TextView measureTextView;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            Ingredient currentIngredient = mIngredients.get(position);
            String ingredient = currentIngredient.getmIngredient();
            double quantity = currentIngredient.getmQuantity();
            String measure = currentIngredient.getmMeasure();

            ingredientTextView.setText(ingredient);
            quantityTextView.setText(String.valueOf(quantity));
            measureTextView.setText(measure);
        }
    }
}
