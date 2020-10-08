package shared;

import java.io.Serializable;

public class MP3Chunk implements Serializable {

    private SongInfo songInfo;
    private int partNo;
    private byte[] musicFileExtract;

    public MP3Chunk(SongInfo songInfo, int partNo, byte[] musicFileExtract) {
        this.songInfo = songInfo;
        this.partNo = partNo;
        this.musicFileExtract = musicFileExtract;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(SongInfo songInfo) {
        this.songInfo = songInfo;
    }

    public int getPartNo() {
        return partNo;
    }

    public void setPartNo(int partNo) {
        this.partNo = partNo;
    }

    public byte[] getMusicFileExtract() {
        return musicFileExtract;
    }

    public void setMusicFileExtract(byte[] musicFileExtract) {
        this.musicFileExtract = musicFileExtract;
    }

    @Override
    public String toString() {
        return "MP3Chunk{" +
                "" + songInfo.getSongTitle() +
                "|part" + partNo +
                " of size=" + musicFileExtract.length +
                '}';
    }
}
