import java.util.ArrayList;
import java.util.Scanner;

// Interface
interface BankOperations {
    void deposit(double amount);
    void withdraw(double amount);
    void checkBalance();
    void showTransactions();
}

// Abstract class
abstract class Account implements BankOperations {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    protected ArrayList<String> transactions = new ArrayList<>();

    public Account(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;

        transactions.add("Account created with balance: ₹" + balance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }

        balance += amount;
        transactions.add("Deposited: ₹" + amount);
        System.out.println("Amount deposited successfully.");
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        balance -= amount;
        transactions.add("Withdrawn: ₹" + amount);
        System.out.println("Amount withdrawn successfully.");
    }

    @Override
    public void checkBalance() {
        System.out.println("Current Balance: ₹" + balance);
    }

    @Override
    public void showTransactions() {
        System.out.println("\n----- Transaction History -----");

        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public abstract void accountType();

    public void displayAccountDetails() {
        System.out.println("\n----- Account Details -----");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + accountHolder);
        System.out.print("Account Type   : ");
        accountType();
        System.out.println("Balance        : ₹" + balance);
    }
}

// Inheritance
class SavingsAccount extends Account {

    private double minimumBalance = 500;

    public SavingsAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance);
    }

    // Method overriding
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }

        if (getBalance() - amount < minimumBalance) {
            System.out.println(
                "Withdrawal denied. Minimum balance of ₹"
                + minimumBalance + " must be maintained."
            );
            return;
        }

        setBalance(getBalance() - amount);
        transactions.add("Withdrawn: ₹" + amount);
        System.out.println("Amount withdrawn successfully.");
    }

    @Override
    public void accountType() {
        System.out.println("Savings Account");
    }
}

// Main class
public class BankingManagementSystem {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Polymorphism
        Account account = new SavingsAccount(
                "ACC1001",
                "Ashwin K",
                5000
        );

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println("     BANKING MANAGEMENT SYSTEM");
            System.out.println("================================");
            System.out.println("1. Account Details");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    account.displayAccountDetails();
                    break;

                case 2:
                    System.out.print("Enter deposit amount: ₹");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: ₹");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;

                case 4:
                    account.checkBalance();
                    break;

                case 5:
                    account.showTransactions();
                    break;

                case 6:
                    System.out.println(
                        "\nThank you for using the Banking Management System!"
                    );
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);

        scanner.close();
    }
}
