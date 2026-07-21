abstract class MoneyHandler{
    protected MoneyHandler nextHandler;
    public MoneyHandler(){
        this.nextHandler = null;
    }
    public void setNextHandler(MoneyHandler handler){
        this.nextHandler = handler;
    }
    public abstract void dispenser(int amount);
}
class ThousandHandler extends MoneyHandler{
    private int numNotes;
    public ThousandHandler(int numNotes){
        this.numNotes = numNotes;
    }
    @Override
    public void dispenser(int amount){
        int note = amount / 1000;
        if(note>numNotes){
            note=numNotes;
            numNotes=0;
        }
        else{
            numNotes=numNotes-note;
        }
        if (note > 0)
            System.out.println("Dispensing " + note + " x ₹1000 notes.");
        int remainingAmount = amount - (note * 1000);
        if (remainingAmount > 0) {
            if (nextHandler != null) nextHandler.dispenser(remainingAmount);
            else {
                System.out.println("Remaining amount of " + remainingAmount + " cannot be fulfilled (Insufficinet fund in ATM)");
            }
        }
    }
}
class FiveHundredHandler extends MoneyHandler{
    private int numNotes;
    public FiveHundredHandler(int numNotes){
        this.numNotes = numNotes;
    }
    @Override
    public void dispenser(int amount){
        int note = amount / 500;
        if(note>numNotes){
            note=numNotes;
            numNotes=0;
        }
        else{
            numNotes=numNotes-note;
        }
        if (note > 0)
            System.out.println("Dispensing " + note + " x ₹500 notes.");
        int remainingAmount = amount - (note * 500);
        if (remainingAmount > 0) {
            if (nextHandler != null) nextHandler.dispenser(remainingAmount);
            else {
                System.out.println("Remaining amount of " + remainingAmount + " cannot be fulfilled (Insufficinet fund in ATM)");
            }
        }
    }
}
class TwoHundredHandler extends MoneyHandler{
    private int numNotes;
    public TwoHundredHandler(int numNotes){
        this.numNotes = numNotes;
    }
    @Override
    public void dispenser(int amount){
        int note = amount / 200;
        if(note>numNotes){
            note=numNotes;
            numNotes=0;
        }
        else{
            numNotes=numNotes-note;
        }
        if (note > 0)
            System.out.println("Dispensing " + note + " x ₹200 notes.");
        int remainingAmount = amount - (note * 200);
        if (remainingAmount > 0) {
            if (nextHandler != null) nextHandler.dispenser(remainingAmount);
            else {
                System.out.println("Remaining amount of " + remainingAmount + " cannot be fulfilled (Insufficinet fund in ATM)");
            }
        }
    }
}
class HundredHandler extends MoneyHandler{
    private int numNotes;
    public HundredHandler(int numNotes){
        this.numNotes = numNotes;
    }
    @Override
    public void dispenser(int amount){
        int note = amount / 100;
        if(note>numNotes){
            note=numNotes;
            numNotes=0;
        }
        else{
            numNotes=numNotes-note;
        }
        if (note > 0)
            System.out.println("Dispensing " + note + " x ₹100 notes.");
        int remainingAmount = amount - (note * 100);
        if (remainingAmount > 0) {
            if (nextHandler != null) nextHandler.dispenser(remainingAmount);
            else {    
                System.out.println("Remaining amount of " + remainingAmount + " cannot be fulfilled (Insufficinet fund in ATM)");
            }
        }
    }
}
public class ChainOfResponsibilities{
    public static void main(String[] args) {
        MoneyHandler handler = new ThousandHandler(3);
        MoneyHandler handler2 = new FiveHundredHandler(5);
        MoneyHandler handler3 = new TwoHundredHandler(7);
        MoneyHandler handler4 = new HundredHandler(20);
        handler.setNextHandler(handler2);
        handler2.setNextHandler(handler3);
        handler3.setNextHandler(handler4);
        System.out.println("Dispensing amount: 5000");
        handler.dispenser(5000);
    }
}