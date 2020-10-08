package com.example.musicstreamingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicstreamingapp.db.DB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shared.ArtistName;
import shared.NodeInfo;
import shared.SongInfo;

public class SongSearchActivity extends AppCompatActivity {
    TextInputEditText edtArtist;
    EditText edtSong;
    ImageView searchBtn;
    FloatingActionButton savedSongsBtn;

    private Map<NodeInfo, List<ArtistName>> brokerData;
    public static String knownBrokerIp = "192.168.2.3";
    public static int knownBrokerPort = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_search);
    }

    @Override
    protected void onStart() {
        super.onStart();

        edtArtist = findViewById(R.id.artist);
        edtSong = findViewById(R.id.edt_song);
        searchBtn = findViewById(R.id.search);
        savedSongsBtn = findViewById(R.id.savedSongsBtn);

        initServerConnection();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForArtist();
            }
        });
        savedSongsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSavedSongsTab();
            }
        });

        edtArtist.setText("Brian Boyko");
//        edtSong.setText("Marked");

    }

    private void initServerConnection() {
        // TODO initiate connection with broker
        if (isNetworkConnected()) {
            if (registerToBroker()) {
                Toast.makeText(this, "You are connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "You are not connected to the internet", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean registerToBroker() {
        System.out.println("Trying to connect to server...");
        try {
            setBrokerData((Map<NodeInfo, List<ArtistName>>) new ConnectionUtil().execute(knownBrokerIp, knownBrokerPort, "Show me the money").get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Brokers total is : " + getBrokerData().size());
        return getBrokerData() != null;
    }


    private void searchForArtist() {
        if (isNetworkConnected() && getBrokerData() != null) {
            String artist = edtArtist.getText().toString();
//            String title = edtSong.getText().toString();
            Toast.makeText(this, "Searching for songs of " + artist + "...", Toast.LENGTH_SHORT).show();

            // get songs (info) of artist
            List<SongInfo> songInfos = getSongsOfArtist(artist);
            if (songInfos == null || songInfos.isEmpty()) {
                Toast.makeText(this, "Could not find songs for this artist", Toast.LENGTH_SHORT).show();
                return;
            }
            NodeInfo broker = findCorrectBrokerByArtistName(artist).keySet().iterator().next();
            showSongInfoRecycler(broker, songInfos);

        } else {
            Toast.makeText(this, "You are offline", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSongInfoRecycler(NodeInfo broker, List<SongInfo> songInfos) {
        DB.setSongInfos(songInfos);
        DB.setNodeInfo(broker);
        Intent intent = new Intent(this, SongsInfoActivity.class);
        startActivity(intent);
    }

    private List<SongInfo> getSongsOfArtist(String requestedArtist) {
        Map<NodeInfo, ArtistName> brokerArtistNameMap = findCorrectBrokerByArtistName(requestedArtist);
        if (brokerArtistNameMap == null) return null;
        NodeInfo broker = brokerArtistNameMap.keySet().iterator().next();
        ArtistName artist = brokerArtistNameMap.get(broker);
        System.out.println("Ask broker: " + broker.getIp() + ":" + broker.getPort() + " for " + artist.getArtistName());
        List<SongInfo> songInfoList = null;
        try {
            songInfoList = (List<SongInfo>) new ConnectionUtil().execute(broker.getIp(), broker.getPort(), artist).get();
            return songInfoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private Map<NodeInfo, ArtistName> findCorrectBrokerByArtistName(String artistString) {
        Map<NodeInfo, ArtistName> result = new HashMap<>();
        Set<Map.Entry<NodeInfo, List<ArtistName>>> entrySet = getBrokerData().entrySet();
        System.out.println("Entry set " + entrySet);
        for (Map.Entry entry : entrySet) {
            for (ArtistName artistName : (List<ArtistName>) entry.getValue()) {
                if (artistString.equals(artistName.getArtistName())) {
                    result.put((NodeInfo) entry.getKey(), artistName);
                    System.out.println("Found " + entry);
                    return result;
                }
            }
        }
        return null;
    }


    private void switchToSavedSongsTab() {
        Intent intent = new Intent(this, SavedSongsActivity.class);
        startActivity(intent);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public Map<NodeInfo, List<ArtistName>> getBrokerData() {
        return this.brokerData;
    }

    public void setBrokerData(Map<NodeInfo, List<ArtistName>> brokerData) {
        this.brokerData = brokerData;
    }
}
