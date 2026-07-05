import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

//Interface for document elements
interface DocumentElement {
    public abstract String render();
}
// Concrete class for text elements
class TextElement implements DocumentElement {
    private String text;

    public TextElement(String text) {
        this.text = text;
    }

    @Override
    public String render() {
        return text;
    }
}
// Concrete class for image elements
class ImageElement implements DocumentElement {
    private String imagePath;

    public ImageElement(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String render() {
        return "[Image: " + imagePath + "]";
    }
}
// NewLineElement class to represent a new line in the document
class NewLineElement implements DocumentElement {
    @Override
    public String render() {
        return "\n";
    }
}
// TabSpaceElement class to represent a tab space in the document
class TabSpaceElement implements DocumentElement {
    @Override
    public String render() {
        return "\t";
    }
}
// Document class responsible for holding a collection of elements
class Document {
    private List<DocumentElement> elements= new ArrayList<>();
    public void addElement(DocumentElement element) {
        elements.add(element);
    }
    // Render the document by iterating through the elements and calling their render method
    public String render() {
        StringBuilder sb = new StringBuilder();
        for (DocumentElement element : elements) {
            sb.append(element.render());
        }
        return sb.toString();
    }
}
// Persistance Interface for saving documents
interface Persistance {
    void save(String content);
}
// FileStorage class implementing the Persistance interface to save documents to a file
class FileStorage implements Persistance {
    @Override
    public void save(String content) {
        try {
            FileWriter writer = new FileWriter("document.txt");
            writer.write(content);
            writer.close();
            System.out.println("Document saved to document.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the document.");
            e.printStackTrace();
        }
    }
}
// DBStorage class implementing the Persistance interface to save documents to a database
class DBStorage implements Persistance {
    @Override
    public void save(String content) {
        // Simulate saving to a database
        System.out.println("Document saved to the database: " + content);
    }
}
// DocumentEditor class responsible for managing the document and its persistence
class DocumentEditor {
    private Document document;
    private Persistance persistance;
    private String renderedDocument="";

    public DocumentEditor(Document document, Persistance persistance) {
        this.document = document;
        this.persistance = persistance;
    }
    // Add text element to the document
    public void addText(String text) {
        document.addElement(new TextElement(text));
        document.addElement(new NewLineElement());
    }
    // Add image element to the document
    public void addImage(String imagePath) {
        document.addElement(new ImageElement(imagePath));
        document.addElement(new NewLineElement());
    }
    // Add a new line element to the document
    public void addNewLine() {
        document.addElement(new NewLineElement());
    }
    // Add a tab space element to the document
    public void addTabSpace() {
        document.addElement(new TabSpaceElement());
    }
    // Render the document by calling the render method of the Document class
    public String renderDocument() {
        if(renderedDocument.isEmpty()) {
            renderedDocument = document.render();
        }
        return renderedDocument;
    }
    // Save the rendered document using the specified persistence mechanism
    public void saveDocument() {
        persistance.save(renderDocument());
    }
}
// Main class to demonstrate the DocumentEditor
public class GoodDesign {
    public static void main(String[] args) {
        Document document = new Document();
        // Choose the persistence mechanism (FileStorage or DBStorage)
        Persistance persistance = new FileStorage(); // or new DBStorage();
        DocumentEditor editor = new DocumentEditor(document, persistance);
        editor.addText("This is a sample document.");
        editor.addImage("image1.png");
        editor.addText("This is another line of text.");
        editor.addImage("image2.jpg");
        System.out.println("Rendered Document:\n" + editor.renderDocument());
        editor.saveDocument();
    }
}