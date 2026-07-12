package strategies;
import models.*;
import java.util.*;


public class RandomStrategy implements PlayStrategy {
    private Playlist currentPlaylist;
    private List<Songs> remainingSongs;
    private Random random;
    private Stack<Songs> history;

    public RandomStrategy(){
        currentPlaylist=null;
        random=new Random();
    }
    @Override
    public void setPlaylist(Playlist playlist){
        currentPlaylist=playlist;
        if( currentPlaylist == null || currentPlaylist.getSize() == 0) return;
        remainingSongs=new ArrayList<>(currentPlaylist.getSongList());
        history=new Stack<>();
    }
    @Override
    public boolean hasNext(){
        return currentPlaylist!=null && !remainingSongs.isEmpty();
    }
    @Override
    public boolean hasPrevious(){
        return history.size()>0;
    }
    @Override
    public Songs next(){
        if(currentPlaylist == null || currentPlaylist.getSize() == 0){
            throw new RuntimeException("No Playlist Loaded or Playlist is empty");
        }
        if(remainingSongs.isEmpty()){
            throw new RuntimeException("You have played all the songs in this playlist");
        }
        int index=random.nextInt(remainingSongs.size());
        Songs selectedSong=remainingSongs.get(index);
        int lastIndex=remainingSongs.size()-1;
        remainingSongs.set(index, remainingSongs.get(lastIndex));
        remainingSongs.remove(lastIndex);
        history.push(selectedSong);
        return selectedSong;
    }
    @Override
    public Songs previous(){
        if(currentPlaylist == null || currentPlaylist.getSize() == 0){
            throw new RuntimeException("No Playlist Loaded or Playlist is empty");
        }
        Songs song=history.pop();
        return song;
    }
}
