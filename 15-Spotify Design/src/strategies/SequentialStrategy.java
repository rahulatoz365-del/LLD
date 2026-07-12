package strategies;
import models.*;

public class SequentialStrategy implements PlayStrategy {
    private Playlist currentPlaylist;
    private int currentIndex;
    public SequentialStrategy(){
        currentPlaylist=null;
        currentIndex=-1;
    }
    @Override
    public void setPlaylist(Playlist playlist){
        currentPlaylist=playlist;
        currentIndex=-1;
    }
    @Override
    public boolean hasNext(){
        return ((currentIndex+1) < currentPlaylist.getSize());
    }
    @Override
    public boolean hasPrevious(){
        return ((currentIndex-1) > 0);
    }
    @Override
    public Songs next(){
        if(currentPlaylist == null || currentPlaylist.getSize() == 0){
            throw new RuntimeException("No Playlist Loaded or Playlist is empty");
        }
        currentIndex += 1;
        return currentPlaylist.getSongList().get(currentIndex);
    }
    @Override
    public Songs previous(){
        if(currentPlaylist == null || currentPlaylist.getSize() == 0){
            throw new RuntimeException("No Playlist Loaded or Playlist is empty");
        }
        currentIndex -= 1;
        return currentPlaylist.getSongList().get(currentIndex);
    }
}
