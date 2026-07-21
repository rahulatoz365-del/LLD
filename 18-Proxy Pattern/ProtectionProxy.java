interface DocumentReader{
    void unlockPdf(String filePath, String password);
}
class DocumentReaderImpl implements DocumentReader{
    public void unlockPdf(String filePath, String password){
        // Code to unlock PDF file
        System.out.println("Loading PDF file: " + filePath);
        System.out.println("Unlocking PDF file with password: " + password);
        System.out.println("PDF file unlocked successfully.");
        System.out.println("Displaying the PDF file.");
    }
}
class User{
    private boolean premiumMembership;
    private String name;

    public User(boolean premiumMembership, String name){
        this.premiumMembership = premiumMembership;
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public boolean getPremiumMembership(){
        return premiumMembership;
    }
}
class DocumentReaderProxy implements DocumentReader{
    private DocumentReaderImpl documentReaderImpl;
    private User user;

    public DocumentReaderProxy( User user){
        this.documentReaderImpl = new DocumentReaderImpl();
        this.user = user;
    }
    @Override
    public void unlockPdf(String filePath, String password){
        if(!user.getPremiumMembership()){
            System.out.println("User " + user.getName() + " does not have a premium membership. Please upgrade your membership to unlock the PDF file.");
            return;
        }
        documentReaderImpl.unlockPdf(filePath, password);
    }
}
public class ProtectionProxy{
    public static void main(String[] args) {
        User user1 = new User(true, "Aditya");
        System.out.println("Aditya a Premium User tries to unlock PDF.");
        DocumentReader documentReader = new DocumentReaderProxy(user1);
        documentReader.unlockPdf("path/to/file.pdf", "password");

        User user2 = new User(false, "Bhanu");
        System.out.println("Bhanu a Non-Premium User tries to unlock PDF.");
        documentReader = new DocumentReaderProxy(user2);
        documentReader.unlockPdf("path/to/file.pdf", "password");
    }
}