package com.example.mina.bonapptit;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFragment extends Fragment {
    @BindView(R.id.video_instructions)
    SimpleExoPlayerView videoInstructions;
    @BindView(R.id.image_instructions)
    ImageView imageDescription;
    @BindView(R.id.description)
    TextView descriptionTextView;
    private SimpleExoPlayer mExoPlayer;

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
        if (videoUrl != null && !TextUtils.isEmpty(videoUrl)) {
            videoInstructions.setVisibility(View.VISIBLE);
            initVideoPlayer(videoUrl);
        }
    }

    private void initVideoPlayer(String videoUrl) {
        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector(), new DefaultLoadControl());
            videoInstructions.setPlayer(mExoPlayer);
            Uri mediaUri = Uri.parse(videoUrl);
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getContext(), "BakingStep"),
                    new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
        }
    }

    public void setImageView(String imageUrl){
        if (imageUrl != null && !TextUtils.isEmpty(imageUrl)) {
            Picasso.with(getContext()).load(Uri.parse(imageUrl)).into(imageDescription);
            imageDescription.setVisibility(View.VISIBLE);
        }

    }

    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

    }
}
