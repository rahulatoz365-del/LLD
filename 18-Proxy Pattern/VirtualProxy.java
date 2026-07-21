interface Image{
    void display();
}
class RealImage implements Image{
    private String filename;
    public RealImage(String filename){
        this.filename = filename;
        System.out.println("[RealImage] Loading " + filename);
    }
    @Override
    public void display(){
        System.out.println("[RealImage] Displaying " + filename);
    }
}
class ProxyImage implements Image{
    private RealImage realImage;
    private String filename;
    public ProxyImage(String filename){
        this.filename = filename;
        this.realImage = null;
    }
    @Override
    public void display(){
        if(realImage == null){
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}
public class VirtualProxy{
    public static void main(String args[]){
        Image image = new ProxyImage("test_10mb.jpg");
        image.display();
    }
}