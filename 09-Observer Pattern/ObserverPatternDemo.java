import java.util.*;

interface Isubscriber {
    void update();
}

//Observer Interface
interface Ichannel {
    void subscribe(Isubscriber subscriber);
    void unsubscribe(Isubscriber subscriber);
    void notifySubscribers();
}
//Concrete Subscriber: A Youtube channel that observers can subscribe to
class YoutubeChannel implements Ichannel {
    private List<Isubscriber> subscribers = new ArrayList<>();
    private String channelName;
    private String latestVideo;

    public YoutubeChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void subscribe(Isubscriber subscriber) {
        if(!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    @Override
    public void unsubscribe(Isubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers() {
        for (Isubscriber subscriber : subscribers) {
            subscriber.update();
        }
    }
    public void uploadVideo(String videoTitle) {
        this.latestVideo = videoTitle;
        System.out.println("New video uploaded: " + videoTitle);
        notifySubscribers();
    }
    public String getVideoData() {
        return "Channel: " + channelName + ", Latest Video: " + latestVideo;
    }
}

//Concrete Observer: A user who subscribes to the Youtube channel
class User implements Isubscriber {
    private String userName;
    private YoutubeChannel channelName;

    public User(String userName,YoutubeChannel channelName) {
        this.userName = userName;
        this.channelName = channelName;
    }

    @Override
    public void update() {
        System.out.println("Hey"+ userName + ", " + channelName.getVideoData());
    }
}
// Main class to demonstrate the Observer pattern
public class ObserverPatternDemo {
    public static void main(String[] args) {
        YoutubeChannel channel = new YoutubeChannel("TechWorld");
        User user1 = new User("Alice", channel);
        User user2 = new User("Bob", channel);
        channel.subscribe(user1);
        channel.subscribe(user2);
        channel.uploadVideo("Java Observer Pattern Tutorial");
        channel.uploadVideo("Python Observer Pattern Tutorial");
        channel.unsubscribe(user1);
        channel.uploadVideo("C++ Observer Pattern Tutorial");
    }
}