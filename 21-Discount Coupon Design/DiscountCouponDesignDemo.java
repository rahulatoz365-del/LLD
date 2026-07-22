import java.util.*;
import java.util.concurrent.locks.*;
// Strategy Code
interface DiscountStrategy{
    double calculate(double amount);
}
class FlatDiscount implements DiscountStrategy{
    private double amount;
    public FlatDiscount(double amount){
        this.amount = amount;
    }
    @Override
    public double calculate(double baseAmount){
        return Math.min(amount, baseAmount);
    }
}
class PercentDiscount implements DiscountStrategy{
    private double percent;
    public PercentDiscount(double percent){
        this.percent = percent;
    }
    @Override
    public double calculate(double baseAmount){
        return baseAmount * (percent/100.0);
    }
}
class PercentWithCapDiscount implements DiscountStrategy{
    private double percent;
    private double cap;
    public PercentWithCapDiscount(double percent, double cap){
        this.percent = percent;
        this.cap = cap;
    }
    @Override
    public double calculate(double baseAmount){
        return Math.min(cap, baseAmount * (percent/100.0));
    }
}
enum StrategyType{
    FLAT,
    PERCENT, 
    PERCENT_WITH_CAP
}
// Discunt Strategy Manager (Singleton)
class DiscountStrategyManager{
    private static DiscountStrategyManager instance;
    private DiscountStrategyManager(){}
    public static synchronized DiscountStrategyManager getInstance(){
        if(instance == null){
            instance = new DiscountStrategyManager();
        }
        return instance;
    }
    public DiscountStrategy getStrategy(StrategyType type, double arg1, double arg2){
        if(type == StrategyType.FLAT){
            return new FlatDiscount(arg1);
        }
        else if (type == StrategyType.PERCENT){
            return new PercentDiscount(arg1);
        }
        else if (type == StrategyType.PERCENT_WITH_CAP){
            return new PercentWithCapDiscount(arg1, arg2);
        }
        return null;
    }
}
// Models Code
class Product{
    private String name;
    private double price;
    private String category;
    public Product(String name, String category, double price){
        this.name = name;
        this.price = price;
        this.category = category;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public String getCategory(){
        return category;
    }
}
class CartItem{
    private Product product;
    private int quantity;
    public CartItem(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }
    public Product getProduct(){
        return product;
    }
    public double itemTotal(){
        return product.getPrice() * quantity;
    }
}
class Cart{
    private List<CartItem> items = new ArrayList<>();
    private boolean loyaltyMember;
    private double originalTotal=0.0;
    private double currentTotal=0.0;
    private String paymentBank;
    public Cart(){
        this.loyaltyMember = false;
        this.paymentBank = "";
    }
    public void addItem(Product product, int quantity){
        items.add(new CartItem(product, quantity));
        originalTotal += product.getPrice() * quantity;
        currentTotal += product.getPrice() * quantity;
    }
    public double getOriginalTotal(){
        return originalTotal;
    }
    public double getCurrentTotal(){
        return currentTotal;
    }
    public void applyDiscount(double discount){
        currentTotal -= discount;
        if(currentTotal < 0){
            currentTotal = 0;
        }
    }
    public void setLoyaltyMember(boolean loyaltyMember){
        this.loyaltyMember = loyaltyMember;
    }
    public void setPaymentBank(String paymentBank){
        this.paymentBank = paymentBank;
    }
    public String getPaymentBank(){
        return paymentBank;
    }
    public boolean isLoyaltyMember(){
        return loyaltyMember;
    }
    public List<CartItem> getItems(){
        return items;
    }
}
// Coupon Base Class (Chain of Responsibilities)
abstract class Coupon{
    private Coupon next;
    public Coupon(){
        this.next = null;
    }
    public void setNext(Coupon next){
        this.next = next;
    }
    public Coupon getNext(){
        return next;
    }
    public void applyDiscount(Cart cart){
        if(isApplicable(cart)){
            double discount = getDiscount(cart);
            cart.applyDiscount(discount);
            System.out.println(name() + "Discount applied: $" + discount);
            if(!isCombinable()){
                return;
            }
        }
        if(next != null){
            next.applyDiscount(cart);
        }
    }
    public abstract boolean isApplicable(Cart cart);
    public abstract double getDiscount(Cart cart);
    public boolean isCombinable(){
        return true;
    }
    public abstract String name();
}
// Concrete Coupons 
class LoyaltyDiscount extends Coupon{
    private double percent;
    private DiscountStrategy discStrategy;
    public LoyaltyDiscount(double percent){
        this.percent = percent;
        this.discStrategy = DiscountStrategyManager.getInstance().getStrategy(StrategyType.PERCENT, percent, 0);
    }
    @Override
    public boolean isApplicable(Cart cart){
        return cart.isLoyaltyMember();
    }
    @Override
    public double getDiscount(Cart cart){
        return discStrategy.calculate(cart.getCurrentTotal());
    }
    @Override
    public String name(){
        return "Loyalty Discount (" + percent + "%)";
    }
}
class SeasonalOffer extends Coupon{
    private double percent;
    private DiscountStrategy discStrategy;
    private String category;
    public SeasonalOffer(double percent, String category){
        this.percent = percent;
        this.category = category;
        this.discStrategy = DiscountStrategyManager.getInstance().getStrategy(StrategyType.PERCENT, percent, 0);
    }
    @Override
    public boolean isApplicable(Cart cart){
        for(CartItem item : cart.getItems()){
            if(item.getProduct().getCategory().equals(category)){
                return true;
            }
        }
        return false;
    }
    @Override
    public double getDiscount(Cart cart){
        double total = 0.0;
        for(CartItem item : cart.getItems()){
            if(item.getProduct().getCategory().equals(category)){
                total += item.itemTotal();
            }
        }
        return discStrategy.calculate(total);
    }
    @Override
    public String name(){
        return "Seasonal Offer (" + percent + "%)";
    }
}
class BulkPurchaseDiscount extends Coupon{
    private double threshold;
    private double flatOff;
    private DiscountStrategy discStrategy;
    public BulkPurchaseDiscount(double threshold, double flatOff){
        this.threshold = threshold;
        this.flatOff = flatOff;
        this.discStrategy = DiscountStrategyManager.getInstance().getStrategy(StrategyType.FLAT, flatOff, 0);
    }
    @Override
    public boolean isApplicable(Cart cart){
        return cart.getCurrentTotal() >= threshold;
    }
    @Override
    public double getDiscount(Cart cart){
        return discStrategy.calculate(cart.getCurrentTotal());
    }
    @Override
    public String name(){
        return "Bulk Purchase Discount ($" + flatOff + ")";
    }
}
class BankDiscount extends Coupon{
    private String bank;
    private DiscountStrategy discStrategy;
    private double minSpend;
    private double flatOff;
    private double percent;
    public BankDiscount(String bank, double minSpend, double flatOff, double percent){
        this.bank = bank;
        this.minSpend = minSpend;
        this.flatOff = flatOff;
        this.percent = percent;
        this.discStrategy = DiscountStrategyManager.getInstance().getStrategy(StrategyType.PERCENT_WITH_CAP, percent, flatOff);
    }
    @Override
    public boolean isApplicable(Cart cart){
        return cart.getPaymentBank().equals(bank) && cart.getOriginalTotal() >= minSpend;
    }
    @Override
    public double getDiscount(Cart cart){
        return discStrategy.calculate(cart.getCurrentTotal());
    }
    @Override
    public String name(){
        return "Bank Discount (" + bank + ")";
    }
}
// Coupon Manager (Singleton)
class CouponManager{
    private static CouponManager instance;
    private Coupon head;
    private final Lock lock=new ReentrantLock();
    private CouponManager(){
        this.head = null;
    }
    public static synchronized CouponManager getInstance(){
        if(instance == null){
            instance = new CouponManager();
        }
        return instance;
    }
    public void registerCoupon(Coupon coupon){
        lock.lock();
        try{
            if(head == null){
                head = coupon;
            }
            else{
                Coupon temp = head;
                while(temp.getNext() != null){
                    temp = temp.getNext();
                }
                temp.setNext(coupon);
            }
        }
        finally{
            lock.unlock();
        }
    }
    public List<String> getApplicable(Cart cart){
        lock.lock();
        try{
            List<String> applicable = new ArrayList<String>();
            Coupon temp = head;
            while(temp != null){
                if(temp.isApplicable(cart)){
                    applicable.add(temp.name());
                }
                temp = temp.getNext();
            }
            return applicable;
        }
        finally{
            lock.unlock();
        }
    }
    public double applyAll(Cart cart){
        lock.lock();
        try{
            if(head!=null){
                head.applyDiscount(cart);
            }
            return cart.getCurrentTotal();
        }
        finally{
            lock.unlock();
        }
    }
}
// Main Class
public class DiscountCouponDesignDemo{
    public static void main(String args[]){
        CouponManager mgr = CouponManager.getInstance();
        mgr.registerCoupon(new SeasonalOffer(10, "Clothing"));
        mgr.registerCoupon(new LoyaltyDiscount(5));
        mgr.registerCoupon(new BulkPurchaseDiscount(1000, 100));
        mgr.registerCoupon(new BankDiscount("ABC", 2000, 15, 500));
        Product p1 = new Product("Winter Jacket", "Clothing", 1000);
        Product p2 = new Product("Smartphone", "Electronics", 20000);
        Product p3 = new Product("Jeans", "Clothing", 1000);
        Product p4 = new Product("Headphones", "Electronics", 2000);
        Cart cart = new Cart();
        cart.addItem(p1, 1);
        cart.addItem(p2, 1);
        cart.addItem(p3, 2);
        cart.addItem(p4, 1);
        cart.setLoyaltyMember(true);
        cart.setPaymentBank("ABC");
        System.out.println("Original Cart Total: " + cart.getOriginalTotal() + " Rs");
        List<String> applicable = mgr.getApplicable(cart);
        System.out.println("Applicable Coupons:");
        for (String name : applicable) {
            System.out.println(" - " + name);
        }
        double finalTotal = mgr.applyAll(cart);
        System.out.println("Final Cart Total after discounts: " + finalTotal + " Rs");
    }
}