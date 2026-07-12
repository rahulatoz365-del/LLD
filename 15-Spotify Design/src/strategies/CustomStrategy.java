package strategies;
import models.*;
import java.util.*;

public class CustomStrategy implements PlayStrategy {
    private Playlist currentPlaylist;
    private int currentIndex;
    private Queue<Songs> nextQueue;
    private Stack<Songs> prevStack;

    private Songs nextSequential() {
        if (currentPlaylist.getSize() == 0) {
            throw new RuntimeException("Playlist is empty.");
        }
        currentIndex = currentIndex + 1;
        return currentPlaylist.getSongList().get(currentIndex);
    }

    private Songs previousSequential() {
        if (currentPlaylist.getSize() == 0) {
            throw new RuntimeException("Playlist is empty.");
        }
        currentIndex = currentIndex - 1;
        return currentPlaylist.getSongList().get(currentIndex);
    }

    public CustomStrategy() {
        currentPlaylist = null;
        currentIndex = -1;
        nextQueue = new LinkedList<>();
        prevStack = new Stack<>();
    }

    @Override
    public void setPlaylist(Playlist playlist) {
        currentPlaylist = playlist;
        currentIndex = -1;
        nextQueue.clear();
        prevStack.clear();
    }

    @Override
    public boolean hasNext() {
        return ((currentIndex + 1) < currentPlaylist.getSize());
    }

    @Override
    public Songs next() {
        if (currentPlaylist == null || currentPlaylist.getSize() == 0) {
            throw new RuntimeException("No playlist loaded or playlist is empty.");
        }

        if (!nextQueue.isEmpty()) {
            Songs s = nextQueue.poll();
            prevStack.push(s);

            // update index to match queued song
            for (int i = 0; i < currentPlaylist.getSongList().size(); ++i) {
                if (currentPlaylist.getSongList().get(i) == s) {
                    currentIndex = i;
                    break;
                }
            }
            return s;
        }

        // Otherwise sequential
        return nextSequential();
    }

    @Override
    public boolean hasPrevious() {
        return (currentIndex - 1 > 0);
    }

    @Override
    public Songs previous() {
        if (currentPlaylist == null || currentPlaylist.getSize() == 0) {
            throw new RuntimeException("No playlist loaded or playlist is empty.");
        }

        if (!prevStack.isEmpty()) {
            Songs s = prevStack.pop();

            // update index to match stacked song
            for (int i = 0; i < currentPlaylist.getSongList().size(); ++i) {
                if (currentPlaylist.getSongList().get(i) == s) {
                    currentIndex = i;
                    break;
                }
            }
            return s;
        }

        // Otherwise sequential
        return previousSequential();
    }

    @Override
    public void addToNext(Songs song) {
        if (song == null) {
            throw new RuntimeException("Cannot enqueue null song.");
        }
        nextQueue.add(song);
    }
}