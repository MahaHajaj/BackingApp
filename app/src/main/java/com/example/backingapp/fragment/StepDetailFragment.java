package com.example.backingapp.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backingapp.MainActivity;
import com.example.backingapp.R;
import com.example.backingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = StepDetailFragment.class.getSimpleName();
    private static MediaSessionCompat mMediaSession;
    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;
    @BindView(R.id.step)
    TextView step;
    @BindView(R.id.next_step)
    Button nextStep;
    @BindView(R.id.prev_step)
    Button prevStep;
    Step steps;
    ArrayList<Step> stepArrayList;
    SimpleExoPlayer player;
    private PlaybackStateCompat.Builder mStateBuilder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_detail, container, false);
        ButterKnife.bind(this, view);
        if (MainActivity.getPane()) {
            stepArrayList = (ArrayList<Step>) getArguments().getSerializable("stepsArraylist");
            steps = (Step) getArguments().getSerializable("steps");

        } else {
            Intent intent = getActivity().getIntent();
            stepArrayList = (ArrayList<Step>) intent.getSerializableExtra("stepsArraylist");
            steps = (Step) intent.getSerializableExtra("steps");
        }

        stepDetail();

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = steps.getId();
                if (id < stepArrayList.size() - 1) {
                    releasePlayer();
                    steps = stepArrayList.get(id + 1);
                    stepDetail();
                } else {
                    Toast.makeText(getContext(), "This is last step", Toast.LENGTH_LONG).show();
                }
            }
        });
        prevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = steps.getId();
                if (id != 0) {
                    releasePlayer();
                    steps = stepArrayList.get(id - 1);
                    stepDetail();
                } else {
                    Toast.makeText(getContext(), "This is first step", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void stepDetail() {
        step.setText(steps.getDescription());
        initializeMediaSession();
        initializePlayer(Uri.parse(steps.getVideoURL()));
    }

    private void initializeMediaSession() {

        mMediaSession = new MediaSessionCompat(getActivity(), TAG);

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new SessionCallback());

        mMediaSession.setActive(true);
    }

    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            playerView.setPlayer(player);

            player.addListener(this);

            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    player.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    player.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            nextStep.setVisibility(View.GONE);
            prevStep.setVisibility(View.GONE);
            step.setVisibility(View.GONE);
            playerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            playerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    private class SessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            if (player != null) {
                player.setPlayWhenReady(true);
            }
        }

        @Override
        public void onPause() {
            super.onPause();
            if (player != null) {
                player.setPlayWhenReady(false);
                player.stop();
                player.release();
                player = null;
            }
        }

        @Override
        public void onSkipToPrevious() {
            player.seekTo(0);
        }
    }
}
