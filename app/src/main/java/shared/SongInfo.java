package shared;

import java.io.Serializable;

public class SongInfo implements Serializable {
    private String songTitle;
    private String artistName;
    private String albumDetails;
    private String genre;
    private String songPath;
    private int partsTotal;

    public SongInfo(String songTitle, String artistName, String albumDetails, String genre, String songPath, int partsTotal) {
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.albumDetails = albumDetails;
        this.genre = genre;
        this.songPath = songPath;
        this.partsTotal = partsTotal;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumDetails() {
        return albumDetails;
    }

    public String getGenre() {
        return genre;
    }

    public String getSongPath() {
        return songPath;
    }

    public int getPartsTotal() {
        return partsTotal;
    }

    @Override
    public String toString() {
        return "SongInfo{" +
                "songTitle='" + songTitle + '\'' +
                ", artistName='" + artistName + '\'' +
//                ", albumDetails='" + albumDetails + '\'' +
//                ", genre='" + genre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongInfo songInfo = (SongInfo) o;

        return songTitle != null ? songTitle.equals(songInfo.songTitle) : songInfo.songTitle == null;
    }

    @Override
    public int hashCode() {
        return songTitle != null ? songTitle.hashCode() : 0;
    }
}
