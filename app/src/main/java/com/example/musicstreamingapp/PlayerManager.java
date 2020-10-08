package com.example.musicstreamingapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import shared.SongInfo;

public class PlayerManager {

    private MediaPlayer mediaPlayer;
    private int currentlyPlayingPartNo;
    private MusicPlayerView musicPlayerView;
    private WeakReference<Context> contextWeakReference;
    private String songTitle, artist;
    private SongInfo songInfo;

    public PlayerManager(Context context, MusicPlayerView musicPlayerView, SongInfo songInfo) {
        this.contextWeakReference = new WeakReference<>(context);
        this.musicPlayerView = musicPlayerView;
        currentlyPlayingPartNo = 0;
        this.songInfo = songInfo;
        songTitle = songInfo.getSongTitle();
        artist = songInfo.getArtistName();
        String resourceCurrentlyPlaying = artist + "_" + songTitle + "_part" + currentlyPlayingPartNo + ".mp3";

        File file;
        do {
            file = new File(contextWeakReference.get().getCacheDir(), resourceCurrentlyPlaying);
        } while (!file.exists());

        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
        } catch (Exception e) {
            // there are no remaining parts, end the activity
            Log.d("Player", String.valueOf(file));
            Toast.makeText(contextWeakReference.get(), "Song finished", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        this.mediaPlayer = MediaPlayer.create(context, Uri.parse(file.getPath()));
//         = mediaPlayer;
    }

    public void initialize() {
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (moreRemainingParts()) {
                    playNextPart();
                    System.out.println("More parts remaining");
                } else {
                    System.out.println("No more parts remaining");
                    musicPlayerView.finishedPlayback();
                }

            }
        });
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void play() {
        mediaPlayer.start();
    }

    public void destroy() {
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    private boolean moreRemainingParts() {
//        Log.e("Remaining", "Current : " + currentlyPlayingPartNo + "Total : " + songInfo.getPartsTotal());
        return currentlyPlayingPartNo < songInfo.getPartsTotal() - 1;
    }

    private void playNextPart() {

        Log.e("Player", "Finished part " + currentlyPlayingPartNo);
        // seek next part
        currentlyPlayingPartNo++;
        String resourceCurrentlyPlaying = artist + "_" + songTitle + "_part" + currentlyPlayingPartNo + ".mp3";

        File file;
        do {
            file = new File(contextWeakReference.get().getCacheDir(), resourceCurrentlyPlaying);
        } while (!file.exists());

        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            Log.d("Playing from file", String.valueOf(file));
        } catch (Exception e) {
            // there are no remaining parts, end the activity
            Toast.makeText(contextWeakReference.get(), "Song finished", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            // set the player source to the file that has the next part of the song
            mediaPlayer.setDataSource(contextWeakReference.get(), Uri.parse(file.getAbsolutePath()));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
