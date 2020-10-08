package com.example.musicstreamingapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import shared.ChunkRequest;
import shared.MP3Chunk;
import shared.NodeInfo;
import shared.SongInfo;

public class SongSearchTask extends AsyncTask<Serializable, MP3Chunk, Boolean> {
    Context context;

    public SongSearchTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Serializable... serializables) {
        NodeInfo broker = (NodeInfo) serializables[0];
        SongInfo songInfo = (SongInfo) serializables[1];

        System.out.println("Ask broker: " + broker.getIp() + ":" + broker.getPort() + " for " + songInfo.getSongTitle());
        for (int i = 0; i < songInfo.getPartsTotal(); i++) {
            ChunkRequest chunkRequest = new ChunkRequest(songInfo, i);
            MP3Chunk mp3Chunk = (MP3Chunk) ConnectionUtil.sendDataToServer(broker.getIp(), broker.getPort(), chunkRequest);
//            System.out.println("Got chunk of requested song : " + mp3Chunk);
            if (mp3Chunk == null) {
                return false;
            }
            saveFileToCache(mp3Chunk);
//            publishProgress(mp3Chunk);
        }

        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("Async", "Started searching...");
    }

    @Override
    protected void onProgressUpdate(MP3Chunk... mp3Chunks) {
//        super.onProgressUpdate(mp3Chunks[0]);
//        System.out.println("Got chunk of requested song : " + mp3Chunks[0]);
        saveFileToCache(mp3Chunks[0]);
    }

    private void saveFileToCache(MP3Chunk mp3Chunk) {
        try {
            File file = new File(context.getCacheDir(), mp3Chunk.getSongInfo().getArtistName() + "_" + mp3Chunk.getSongInfo().getSongTitle() + "_part" + mp3Chunk.getPartNo() + ".mp3");
            FileOutputStream fw = new FileOutputStream(file.getAbsoluteFile());
            BufferedOutputStream bw = new BufferedOutputStream(fw);
            bw.write(mp3Chunk.getMusicFileExtract());
            bw.close();
            Log.d("Save", "Saved " + mp3Chunk + " to " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.d("Async", "Finished async task successfully");

    }

}
