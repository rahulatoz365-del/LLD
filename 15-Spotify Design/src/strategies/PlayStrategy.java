package strategies;
import models.*;

public interface PlayStrategy {
    void setPlaylist(Playlist playlist);
    Songs next();
    boolean hasNext();
    Songs previous();
    boolean hasPrevious();
    default void addToNext(Songs song){}
}
