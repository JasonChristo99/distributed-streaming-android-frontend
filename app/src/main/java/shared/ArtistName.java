package shared;

import java.io.Serializable;

public class ArtistName implements Comparable<ArtistName>, Serializable {

    private String artistName;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }


    @Override
    public int compareTo(ArtistName artist) {
        if (getArtistName() == null || artist.getArtistName() == null) {
            return 0;
        }
        return getArtistName().compareTo(artist.getArtistName());
    }

    @Override
    public String toString() {
        return "Artist{" +
//                "artistName='" +
                artistName
//                + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistName that = (ArtistName) o;

        return artistName != null ? artistName.equals(that.artistName) : that.artistName == null;
    }

    @Override
    public int hashCode() {
        return artistName != null ? artistName.hashCode() : 0;
    }
}
