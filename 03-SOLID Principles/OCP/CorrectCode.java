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
// This interface is responsible for stating the method to save to database
interface DatabaseSaver {
    void saveToDatabase(ShoppingCart cart);
}

// This class is responsible for saving to database to MySQL
class MySQLDatabaseSaver implements DatabaseSaver {
    @Override
    public void saveToDatabase(ShoppingCart cart) {
        // Code to save the shopping cart to the database
        System.out.println("Saving shopping cart to MySQL database...");
    }
}
// This class is responsible for saving to database to MongoDB
class MongoDBDatabaseSaver implements DatabaseSaver {
    @Override
    public void saveToDatabase(ShoppingCart cart) {
        // Code to save the shopping cart to the database
        System.out.println("Saving shopping cart to MongoDB database...");
    }
}

// Main class to demonstrate the functionality
public class CorrectCode {
    public static void main(String[] args) {
        Product product1 = new Product("Laptop", 1000);
        Product product2 = new Product("Mouse", 50);

        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(product1);
        cart.addProduct(product2);

        InvoicePrinter printer = new InvoicePrinter();
        printer.printInvoice(cart);

        DatabaseSaver mysqlSaver = new MySQLDatabaseSaver();
        mysqlSaver.saveToDatabase(cart);

        DatabaseSaver mongoSaver = new MongoDBDatabaseSaver();
        mongoSaver.saveToDatabase(cart);
    }
}   
