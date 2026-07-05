import java.util.*;

// This class is responsible for managing the product catalog
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

// This class is responsible for managing the shopping cart
class ShoppingCart {
    private List<Product> products= new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }
    public List<Product> getProducts() {
        return products;
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
}

// This class is responsible for printing the invoice
class InvoicePrinter {
    public void printInvoice(ShoppingCart cart) {
        System.out.println("Invoice:");
        for (Product product : cart.getProducts()) {
            System.out.println(product.getName() + " - $" + product.getPrice());
        }
        System.out.println("Total: $" + cart.calculateTotal());
    }
}
// This class is responsible for saving to database
class DatabaseSaver {
    // Code to save the shopping cart to the SQL database
    public void saveToSQL(ShoppingCart cart) {
        // Code to save the shopping cart to the database
        System.out.println("Saving shopping cart to SQL database...");
    }
    // Code to save the shopping cart to the MongoDB database
    public void saveToMongoDB(ShoppingCart cart) {
        System.out.println("Saving shopping cart to MongoDB database...");
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

        InvoicePrinter printer = new InvoicePrinter();
        printer.printInvoice(cart);

        DatabaseSaver dbSaver = new DatabaseSaver();
        dbSaver.saveToSQL(cart);
        dbSaver.saveToMongoDB(cart);
    }
}