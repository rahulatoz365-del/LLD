import java.util.*;

// Product class represents a product in the catalog
class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// ShoppingCart class managing multiple responsibilities: adding/removing products, calculating total, printing invoice, and saving to database
class ShoppingCart {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public double calculateTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public void printInvoice() {
        System.out.println("Invoice:");
        for (Product product : products) {
            System.out.println(product.getName() + " - $" + product.getPrice());
        }
        System.out.println("Total: $" + calculateTotal());
    }

    public void saveToDatabase() {
        // Code to save the shopping cart to the database
        System.out.println("Saving shopping cart to database...");
    }
}
// Main class to demonstrate the functionality
public class WrongCode {
    public static void main(String[] args) {
        Product product1 = new Product("Laptop", 1000);
        Product product2 = new Product("Mouse", 50);

        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(product1);
        cart.addProduct(product2);

        cart.printInvoice();
        cart.saveToDatabase();
    }
}