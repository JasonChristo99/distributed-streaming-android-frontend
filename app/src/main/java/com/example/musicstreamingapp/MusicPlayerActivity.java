package com.example.musicstreamingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicstreamingapp.db.DB;

import shared.SongInfo;

public class MusicPlayerActivity extends AppCompatActivity implements MusicPlayerView {
    ImageView playPauseIv;
    TextView songInfoTv;
    String songTitle;
    private PlayerManager playerManager;
    //    public static final String TEST_SONG_TITLE = "rock";
    public int songId;
    //    public int songPosition;
    public String mode;
    SongInfo songInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Intent intent = getIntent();
        songId = intent.getIntExtra("song_id", -1);
        mode = intent.getStringExtra("mode");
        if (mode.equals("saved")) {
            songInfo = DB.getSaved().get(songId);
        } else if (mode.equals("stream")) {
            songInfo = DB.getSongInfos().get(songId);
        }
        songTitle = songInfo.getSongTitle();
//        songTitle = TEST_SONG_TITLE; // testing

        playPauseIv = findViewById(R.id.play_pause);
        songInfoTv = findViewById(R.id.currently_playing_info_tv);

        handleVisibilities();
        setUpListeners();
    }

    private void handleVisibilities() {
//        if (mode.equals("saved")) return;

        // hide buttons not needed when streaming
        // playPauseIv.setVisibility(View.GONE);
        songInfoTv.setText(songInfo.getSongTitle() + " by " + songInfo.getArtistName());
    }

    private void setUpListeners() {
        // set play and pause listeners
        playPauseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlayPauseClicked();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // start playback and player logic
        playSong();
    }

    private void onPlayPauseClicked() {
        if (playerManager.isPlaying()) {
            playerManager.pause();
            playPauseIv.setImageResource(R.drawable.ic_play);
            Toast.makeText(this, R.string.paused_feedback, Toast.LENGTH_SHORT).show();
        } else {
            playerManager.play();
            playPauseIv.setImageResource(R.drawable.ic_pause);
            Toast.makeText(this, R.string.resume_playing_feedback, Toast.LENGTH_SHORT).show();
        }
    }

    public void playSong() {
        playerManager = new PlayerManager(this, this, songInfo);
        playerManager.initialize();
    }

    @Override
    protected void onDestroy() {
        playerManager.destroy();
        super.onDestroy();
    }


    @Override
    public void finishedPlayback() {
        Toast.makeText(this, "Playback finished", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
