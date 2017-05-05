package com.example.mina.bonapptit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mina.bonapptit.Data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder>{
    private List<Step> mSteps;
    private StepOnClickListener mOnClickListener;


    public interface StepOnClickListener {
        void onClick(int position);
    }

    public StepsAdapter(List<Step> mSteps, StepOnClickListener listener) {
        this.mSteps = mSteps;
        this.mOnClickListener = listener;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.step_item, parent, false);

        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.step_short_description)
        TextView stepShortDescription;

        public StepsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            stepShortDescription.setText(String.valueOf(position+1) + ". " + mSteps.get(position).getmShortDescription());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onClick(position);
        }
    }
}
