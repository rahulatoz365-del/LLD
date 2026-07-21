import java.util.*;

// Payment Request DataStructure
class PaymentRequest{
    private String sender;
    private String receiver;
    private double amount;
    private String currency;
    public PaymentRequest(String sender, String receiver, double amount, String currency){
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.currency = currency;
    }
    // getters 
    public String getSender(){
        return this.sender;
    }
    public String getReceiver(){
        return this.receiver;
    }
    public double getAmount(){
        return this.amount;
    }
    public String getCurrency(){
        return this.currency;
    }
}
// Banking System Interface and Strategy 
interface BankingSystem{
    boolean processPayment(double amount);
}
class PaytmBankingSystem implements BankingSystem{
    private Random random = new Random();
    public PaytmBankingSystem(){}
    @Override
    public boolean processPayment(double amount){
        // process payment using Paytm
        System.out.println("[Banking System: Paytm] Processing payment using Paytm");
        int rand = random.nextInt(100);
        return rand > 80;
    }
}
class PhonePeBankingSystem implements BankingSystem{
    private Random random = new Random();
    public PhonePeBankingSystem(){}
    @Override
    public boolean processPayment(double amount){
        // process payment using Paytm
        System.out.println("[Banking System: PhonePe] Processing payment using PhonePe");
        int rand = random.nextInt(100);
        return rand > 70;
    }
}
class RazorpayBankingSystem implements BankingSystem{
    private Random random = new Random();
    public RazorpayBankingSystem(){}
    @Override
    public boolean processPayment(double amount){
        // process payment using Paytm
        System.out.println("[Banking System: Razorpay] Processing payment using Razorpay");
        int rand = random.nextInt(100);
        return rand > 90;
    }
}
// Abstract Base Class for Payment Gateway
abstract class PaymentGateway{
    protected BankingSystem bankingSystem;
    public PaymentGateway(){
        this.bankingSystem = null;
    }
    // Template method defining the sequence or steps
    public boolean processPayment(PaymentRequest paymentRequest){
        if(!validatePayment(paymentRequest)){
            System.out.println("[Payment Gateway] Validation failed for "+ paymentRequest.getSender());
            return false;
        }
        if(!initiatePayment(paymentRequest)){
            System.out.println("[Payment Gateway] Payment Initiation failed for "+ paymentRequest.getSender());
            return false;
        }
        if(!confirmPayment(paymentRequest)){
            System.out.println("[Payment Gateway] Payment Confirmation failed for "+ paymentRequest.getSender());
            return false;
        }
        System.out.println("[Payment Gateway] Payment processed successfully for "+ paymentRequest.getSender());
        return true;
    }
    // methods to implement by concrete gateways
    protected abstract boolean validatePayment(PaymentRequest paymentRequest);
    protected abstract boolean initiatePayment(PaymentRequest paymentRequest);
    protected abstract boolean confirmPayment(PaymentRequest paymentRequest);
}
// Concrete Payment Gateways
class PaytmPaymentGateway extends PaymentGateway{
    public PaytmPaymentGateway(){
        this.bankingSystem = new PaytmBankingSystem();
    }
    @Override
    protected boolean validatePayment(PaymentRequest paymentRequest){
        System.out.println("[Paytm Payment Gateway] Validating payment for "+ paymentRequest.getSender());
        if(paymentRequest.getCurrency() != "INR" || paymentRequest.getAmount() <= 0){
            return false;
        }
        return true;
    }
    @Override
    protected boolean initiatePayment(PaymentRequest paymentRequest){
        System.out.println("[Paytm Payment Gateway] Initiating payment for "+ paymentRequest.getSender());
        return this.bankingSystem.processPayment(paymentRequest.getAmount());
    }
    @Override
    protected boolean confirmPayment(PaymentRequest paymentRequest){
        System.out.println("[Paytm Payment Gateway] Confirming payment for "+ paymentRequest.getSender());
        return true;
    }
}
class PhonePePaymentGateway extends PaymentGateway{
    public PhonePePaymentGateway(){
        this.bankingSystem = new PhonePeBankingSystem();
    }
    @Override
    protected boolean validatePayment(PaymentRequest paymentRequest){
        System.out.println("[PhonePe Payment Gateway] Validating payment for "+ paymentRequest.getSender());
        if(paymentRequest.getCurrency() != "INR" || paymentRequest.getAmount() <= 0){
            return false;
        }
        return true;
    }
    @Override
    protected boolean initiatePayment(PaymentRequest paymentRequest){
        System.out.println("[PhonePe Payment Gateway] Initiating payment for "+ paymentRequest.getSender());
        return this.bankingSystem.processPayment(paymentRequest.getAmount());
    }
    @Override
    protected boolean confirmPayment(PaymentRequest paymentRequest){
        System.out.println("[PhonePe Payment Gateway] Confirming payment for "+ paymentRequest.getSender());
        return true;
    }
}
class RazorpayPaymentGateway extends PaymentGateway{
    public RazorpayPaymentGateway(){
        this.bankingSystem = new RazorpayBankingSystem();
    }
    @Override
    protected boolean validatePayment(PaymentRequest paymentRequest){
        System.out.println("[Razorpay Payment Gateway] Validating payment for "+ paymentRequest.getSender());
        if(paymentRequest.getCurrency() != "INR" || paymentRequest.getAmount() <= 0){
            return false;
        }
        return true;
    }
    @Override
    protected boolean initiatePayment(PaymentRequest paymentRequest){
        System.out.println("[Razorpay Payment Gateway] Initiating payment for "+ paymentRequest.getSender());
        return this.bankingSystem.processPayment(paymentRequest.getAmount());
    }
    @Override
    protected boolean confirmPayment(PaymentRequest paymentRequest){
        System.out.println("[Razorpay Payment Gateway] Confirming payment for "+ paymentRequest.getSender());    
        return true;
    }
}
// Proxy class for Payment Gateway
class PaymentGatewayProxy extends PaymentGateway{
    private PaymentGateway paymentGateway;
    private int retries;
    public PaymentGatewayProxy(PaymentGateway paymentGateway, int retries){
        this.paymentGateway = paymentGateway;
        this.retries = retries;
    }
    @Override
    public boolean processPayment(PaymentRequest paymentRequest){
        boolean result = false;
        for(int i = 0; i < this.retries; i++){
            if(i>0){
                System.out.println("[Payment Gateway Proxy] Retrying payment for "+ paymentRequest.getSender());
            }
            result = paymentGateway.processPayment(paymentRequest);
            if(result){
                break;
            }
        }
        if(!result){
            System.out.println("[Payment Gateway Proxy] Payment failed for "+ paymentRequest.getSender());
        }
        return result;
    }
    @Override
    public boolean validatePayment(PaymentRequest paymentRequest){
        return paymentGateway.validatePayment(paymentRequest);
    }
    @Override
    public boolean initiatePayment(PaymentRequest paymentRequest){
        return paymentGateway.initiatePayment(paymentRequest);
    }
    @Override
    public boolean confirmPayment(PaymentRequest paymentRequest){
        return paymentGateway.confirmPayment(paymentRequest);
    }
}
// Gateway Factory (Singleton)
enum GatewayType{
    PHONEPE,
    PAYTM,
    RAZORPAY
}
class GatewayFactory{
    private static final GatewayFactory instance = new GatewayFactory();
    private GatewayFactory(){}
    public static GatewayFactory getInstance(){
        return instance;
    }
    public PaymentGateway getGateway(GatewayType gatewayType){
        if(gatewayType == GatewayType.PAYTM){
            PaymentGateway paymentGateway = new PaytmPaymentGateway();
            return new PaymentGatewayProxy(paymentGateway, 3);
        }
        else if(gatewayType == GatewayType.PHONEPE){
            PaymentGateway paymentGateway = new PhonePePaymentGateway();
            return new PaymentGatewayProxy(paymentGateway, 3);
        }
        else{
            PaymentGateway paymentGateway = new RazorpayPaymentGateway();
            return new PaymentGatewayProxy(paymentGateway, 3);
        }
    }
}
// API Service (Singleton)
class PaymentService{
    private static final PaymentService instance = new PaymentService();
    private PaymentGateway paymentGateway;
    
    private PaymentService(){
        this.paymentGateway = null;
    }
    public static PaymentService getInstance(){
        return instance;
    }
    public void setPaymentGateway(PaymentGateway paymentGateway){
        this.paymentGateway = paymentGateway;
    }
    public boolean processPayment(PaymentRequest paymentRequest){
        if(paymentGateway == null){
            System.out.println("[Payment Service] Payment gateway not set");
            return false;
        }
        return paymentGateway.processPayment(paymentRequest);
    }
}
// Controller class for all client requests (Singleton)
class PaymentController{
    private static final PaymentController instance = new PaymentController();
    private PaymentController(){}
    public static PaymentController getInstance(){
        return instance;
    }
    public boolean handlePayment(PaymentRequest paymentRequest , GatewayType gatewayType){
        PaymentGateway paymentGateway = GatewayFactory.getInstance().getGateway(gatewayType);
        PaymentService.getInstance().setPaymentGateway(paymentGateway);
        return PaymentService.getInstance().processPayment(paymentRequest);
    }
}
// Main or Client Code
public class PaymentGatewayDesignDemo {
    public static void main(String[] args) {
        PaymentRequest paymentRequest1 = new PaymentRequest("John Doe","Rahul Singh", 1000, "INR");
        System.out.println("Processing via Paytm");
        System.out.println(" ------------------------------------------------ ");
        boolean result1 = PaymentController.getInstance().handlePayment(paymentRequest1, GatewayType.PAYTM);
        System.out.println("Result: " + (result1 ? "Success" : "Failure"));
        System.out.println(" ------------------------------------------------ ");

        PaymentRequest paymentRequest2 = new PaymentRequest("Aman","Vijay", 1000, "INR");
        System.out.println("Processing via PhonePe");
        System.out.println(" ------------------------------------------------ ");
        boolean result2 = PaymentController.getInstance().handlePayment(paymentRequest2, GatewayType.PHONEPE);
        System.out.println("Result: " + (result2 ? "Success" : "Failure"));
        System.out.println(" ------------------------------------------------ ");

        PaymentRequest paymentRequest3 = new PaymentRequest("Garudip","Jai", 1000, "INR");
        System.out.println("Processing via Razorpay");
        System.out.println(" ------------------------------------------------ ");
        boolean result3 = PaymentController.getInstance().handlePayment(paymentRequest3, GatewayType.RAZORPAY);
        System.out.println("Result: " + (result3 ? "Success" : "Failure"));
        System.out.println(" ------------------------------------------------ ");
    }
}
