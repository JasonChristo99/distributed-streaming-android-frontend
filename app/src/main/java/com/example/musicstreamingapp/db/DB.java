package com.example.musicstreamingapp.db;

import java.util.ArrayList;
import java.util.List;

import shared.NodeInfo;
import shared.SongInfo;

public class DB {
    private static NodeInfo nodeInfo;
    private static List<SongInfo> songInfos;
    private static List<SongInfo> saved = new ArrayList<>();

    public static List<SongInfo> getSongInfos() {
        return DB.songInfos;
    }

    public static void setSongInfos(List<SongInfo> songInfos) {
        DB.songInfos = songInfos;
    }

    public static NodeInfo getNodeInfo() {
        return nodeInfo;
    }

    public static void setNodeInfo(NodeInfo nodeInfo) {
        DB.nodeInfo = nodeInfo;
    }

    public static List<SongInfo> getSaved() {
        return saved;
    }

    public static void addSaved(SongInfo saved) {
        DB.saved.add(saved);
    }
}
