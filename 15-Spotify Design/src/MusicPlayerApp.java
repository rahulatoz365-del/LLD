import managers.*;
import models.*;
import enums.*;
import java.util.*;

public class MusicPlayerApp {
    private static MusicPlayerApp instance = null;
    private List<Songs> songLibrary;
    private MusicPlayerApp(){
        songLibrary=new ArrayList<>();
    }
    public static synchronized MusicPlayerApp getInstance() {
        if (instance == null) {
            instance = new MusicPlayerApp();
        }
        return instance;
    }

    public void createSongInLibrary(String title, String artist, String path) {
        Songs newSong = new Songs(title, artist, path);
        songLibrary.add(newSong);
    }

    public Songs findSongByTitle(String title) {
        for (Songs s : songLibrary) {
            if (s.getTitle().equals(title)) {
                return s;
            }
        }
        return null;
    }

    public void createPlaylist(String playlistName) {
        PlaylistManager.getInstance().createPlaylist(playlistName);
    }

    public void addSongToPlaylist(String playlistName, String songTitle) {
        Songs song = findSongByTitle(songTitle);
        if (song == null) {
            throw new RuntimeException("Songs \"" + songTitle + "\" not found in library.");
        }
        PlaylistManager.getInstance().addSongToPlaylist(playlistName, song);
    }

    public void connectAudioDevice(DeviceType deviceType) {
        MusicPlayerFacade.getInstance().connectDevice(deviceType);
    }

    public void selectPlayStrategy(PlayerStrategy strategyType) {
        MusicPlayerFacade.getInstance().setPlayStrategy(strategyType);
    }

    public void loadPlaylist(String playlistName) {
        MusicPlayerFacade.getInstance().loadPlaylist(playlistName);
    }

    public void playSingleSong(String songTitle) {
        Songs song = findSongByTitle(songTitle);
        if (song == null) {
            throw new RuntimeException("Songs \"" + songTitle + "\" not found.");
        }
        MusicPlayerFacade.getInstance().playSong(song);
    }

    public void pauseCurrentSong(String songTitle) {
        Songs song = findSongByTitle(songTitle);
        if (song == null) {
            throw new RuntimeException("Songs \"" + songTitle + "\" not found.");
        }
        MusicPlayerFacade.getInstance().pauseSong(song);
    }

    public void playAllTracksInPlaylist() {
        MusicPlayerFacade.getInstance().playAllTracks();
    }

    public void playPreviousTrackInPlaylist() {
        MusicPlayerFacade.getInstance().playPreviousTrack();
    }

    public void queueSongNext(String songTitle) {
        Songs song = findSongByTitle(songTitle);
        if (song == null) {
            throw new RuntimeException("Songs \"" + songTitle + "\" not found.");
        }
        MusicPlayerFacade.getInstance().enqueueNext(song);
    }
}
