import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

class DocumentEditor{
    private List<String> documentContent;
    private String renderedDocument;

    public DocumentEditor() {
        this.documentContent = new ArrayList<>();
        this.renderedDocument = "";
    }
    // Add text as plain String
    public void addText(String text) {
        documentContent.add(text);
    }
    // Add image as file path
    public void addImage(String imagePath) {
        documentContent.add(imagePath);
    }
    // Render the document by checking the type of each element at runtime
    public String renderDocument() {
        if(renderedDocument.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String content : documentContent) {
                if (content.length() > 4 && (content.endsWith(".png") || content.endsWith(".jpg") || content.endsWith(".jpeg"))) {
                    sb.append("[Image: ").append(content).append("]\n");
                } else {
                    sb.append(content).append("\n");
                }
            }
            renderedDocument = sb.toString();
        }
        return renderedDocument;
    }
    // Save the rendered document to a file
    public void saveDocument() {
        try{
            FileWriter writer = new FileWriter("document.txt");
            writer.write(renderDocument());
            writer.close();
            System.out.println("Document saved to document.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the document.");
            e.printStackTrace();
        }
    }
}
// Main class to demonstrate the DocumentEditor
public class BadDesign {
    public static void main(String[] args) {
        DocumentEditor editor = new DocumentEditor();
        editor.addText("This is a sample document.");
        editor.addImage("image1.png");
        editor.addText("This is another line of text.");
        editor.addImage("image2.jpg");

        System.out.println("Rendered Document:\n" + editor.renderDocument());
        editor.saveDocument();
    }
}