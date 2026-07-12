package models;
import java.util.List;

import javax.management.RuntimeErrorException;

import java.util.ArrayList;

public class Playlist {
    private String playlistName;
    private List<Songs> songList;
    // Constructor
    public Playlist(String playlistName){
        this.playlistName=playlistName;
        this.songList = new ArrayList<>(); 
    }
    // Getters
    public String getPlaylistName(){
        return playlistName;
    }
    public List<Songs> getSongList(){
        return songList;
    }
    public int getSize(){
        return songList.size();
    }
    // Setter (Add Song Feature)
    public void addSong(Songs song){
        if(song==null){
            throw new RuntimeException("Cannot add song to the playlist try again");
        }
        songList.add(song);
    }
}
