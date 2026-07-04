/* Encapsulation Definition ->  Encapsulation means binding the data (variables) and code (methods) together as a single unit. 
                            It is a mechanism of wrapping the data and code together as a single unit.
                            In encapsulation, the variables of a class will be hidden from other classes, 
                            and can be accessed only through the methods of their current class. 
                            Therefore, it is also known as data hiding.
    Encapsulation Examples ->  Take the example of a Bank Account class. 
                            The account number, account holder name, and balance are private variables. 
                            The methods to deposit and withdraw money are public methods. 
                            The private variables can only be accessed through the public methods, 
                            which ensures that the data is protected from unauthorized access.
                            Since we talked about encapsulation, we can also talk about the access modifiers in Java.
                            Access modifiers are keywords that set the accessibility of classes, methods, and other members.
    Access Modifiers in Java ->  There are four types of access modifiers in Java:
                            1. Private: The member is accessible only within the class.
                            2. Default: The member is accessible only within the package.
                            3. Protected: The member is accessible within the package and by subclasses.
                            4. Public: The member is accessible from any other class.
*/

// BankAccount class with private variables and public methods to access them.
class BankAccount {
    private String accountNumber; // Private variable
    private String accountHolderName; // Private variable
    private double balance; // Private variable

    // Constructor to initialize the BankAccount object
    public BankAccount(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    // Public method to deposit money into the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Public method to withdraw money from the account
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }

    // Public method to get the current balance
    public double getBalance() {
        return balance;
    }
    // Public method to get the account number
    public String getAccountNumber() {
        return accountNumber;
    }
    // Public method to get the account holder name
    public String getAccountHolderName() {
        return accountHolderName;
    }
}
// Main method to demonstrate encapsulation
public class Encapsulation {
    public static void main(String[] args) {
        // Creating a BankAccount object
        BankAccount account = new BankAccount("123678", "Rahul", 100066.0);
        // Accessing the public methods to interact with the private variables
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Holder Name: " + account.getAccountHolderName());
        System.out.println("Initial Balance: " + account.getBalance());
        account.deposit(5000.0);
        System.out.println("Balance after deposit: " + account.getBalance());
        account.withdraw(2000.0);
        System.out.println("Balance after withdrawal: " + account.getBalance());
    }
}