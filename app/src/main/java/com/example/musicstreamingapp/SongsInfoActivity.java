package com.example.musicstreamingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicstreamingapp.adapter.SavedSongAdapter;
import com.example.musicstreamingapp.adapter.SongsInfoAdapter;
import com.example.musicstreamingapp.db.DB;
import com.example.musicstreamingapp.model.Song;

import org.w3c.dom.Node;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import shared.ChunkRequest;
import shared.MP3Chunk;
import shared.NodeInfo;
import shared.SongInfo;

public class SongsInfoActivity extends AppCompatActivity implements SongsInfoAdapter.SongsInfoInteraction {
    RecyclerView songInfosRView;
    NodeInfo broker;
    List<SongInfo> songInfos;
    SongsInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_info);

        songInfosRView = findViewById(R.id.songsInfoList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        broker = DB.getNodeInfo();
        songInfos = DB.getSongInfos();

        initListView();
    }

    private void initListView() {
        adapter = new SongsInfoAdapter(new ArrayList<>(songInfos));
        adapter.setListener(this);
        songInfosRView.setHasFixedSize(true);
        songInfosRView.setAdapter(adapter);
    }

    @Override
    public void playSong(int id) {
        new SongSearchTask(getApplicationContext()).execute(broker, DB.getSongInfos().get(id));
        Intent intent = new Intent(this, MusicPlayerActivity.class);
        intent.putExtra("song_id", id);
        intent.putExtra("mode", "stream");
        startActivity(intent);
    }

    @Override
    public void saveSong(int position) {
        new SongSearchTask(getApplicationContext()).execute(broker, songInfos.get(position));
        DB.addSaved(songInfos.get(position));
    }


}
