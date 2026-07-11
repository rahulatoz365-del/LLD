import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Base Interface for files and folders
interface FileSystemComponent {
    void ls(int indent);
    void openAll(int indent);
    int getSize();
    FileSystemComponent cd(String name);
    String getName();
    boolean isFolder();
}
// Leaf class representing a file
class Files implements FileSystemComponent{
    private String name;
    private int size;
    public Files(String name, int size){
        this.name=name;
        this.size=size;
    }
    @Override
    public void ls(int indent){
        String indentSpaces = " ".repeat(indent);
        System.out.println(indentSpaces + name);
    }
    @Override
    public void openAll(int indent){
        String indentSpaces = " ".repeat(indent);
        System.out.println(indentSpaces + name);
    }
    @Override
    public int getSize(){
        return size;
    }
    @Override
    public FileSystemComponent cd(String name) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isFolder() {
        return false;
    }
}
class Folders implements FileSystemComponent{
    private String name;
    private List<FileSystemComponent> children;
    public Folders ( String name ){
        this.name=name;
        children=new ArrayList<>();
    }
    public void add (FileSystemComponent item){
        children.add(item);
    }
    @Override
    public void ls(int indent){
        String indentSpaces = " ".repeat(indent);
        for (FileSystemComponent child : children) {
            if (child.isFolder()) {
                System.out.println(indentSpaces + "+ " + child.getName());
            } else {
                System.out.println(indentSpaces + child.getName());
            }
        }
    }
    @Override
    public void openAll(int indent) {
        String indentSpaces = " ".repeat(indent);
        System.out.println(indentSpaces + "+ " + name);
        for (FileSystemComponent child : children) {
            child.openAll(indent + 4);
        }
    }
    @Override
    public int getSize() {
        int total = 0;
        for (FileSystemComponent child : children) {
            total += child.getSize();
        }
        return total;
    }
    @Override
    public FileSystemComponent cd(String target) {
        for (FileSystemComponent child : children) {
            if (child.isFolder() && child.getName().equals(target)) {
                return child;
            }
        }
        // not found or not a folder
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isFolder() {
        return true;
    }
}
// Main/Client Code
public class CompositePatternDemo {
    public static void main(String[] args) {
        // Build file system
        Folders root = new Folders("root");
        root.add(new Files("file1.txt", 1));
        root.add(new Files("file2.txt", 1));

        Folders docs = new Folders("docs");
        docs.add(new Files("resume.pdf", 1));
        docs.add(new Files("notes.txt", 1));
        root.add(docs);

        Folders images = new Folders("images");
        images.add(new Files("photo.jpg", 1));
        root.add(images);

        root.ls(0);

        docs.ls(0);

        root.openAll(0);

        FileSystemComponent cwd = root.cd("docs");
        if (cwd != null) {
            cwd.ls(0);
        } else {
            System.out.println("\nCould not cd into docs\n");
        }

        System.out.println(root.getSize());
    }
}