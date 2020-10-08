package shared;

import java.io.Serializable;

public class ChunkRequest implements Serializable {
    private SongInfo songInfo;
    private int partNo;

    public ChunkRequest(SongInfo songInfo, int partNo) {
        this.songInfo = songInfo;
        this.partNo = partNo;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public int getPartNo() {
        return partNo;
    }

    @Override
    public String toString() {
        return "ChunkRequest{" +
                "songInfo=" + songInfo +
                ", partNo=" + partNo +
                '}';
    }
}
