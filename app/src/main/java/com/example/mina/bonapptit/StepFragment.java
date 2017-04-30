package com.example.mina.bonapptit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFragment extends Fragment {
    @BindView(R.id.video_instructions)
    VideoView videoInstructions;
    @BindView(R.id.image_instructions)
    ImageView imageDescription;
    @BindView(R.id.description)
    TextView descriptionTextView;

    public StepFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void setVideoView(String videoUrl) {
    }

    public void setImageView(String imageUrl){
    }

    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }
}
