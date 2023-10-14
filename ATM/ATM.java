import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

class Transaction // The transaction class is created.
{
    private String type;
    private int amount;
    private Date timestamp;

    public Transaction(String type, int amount)// This Constructor is used to get the current string type,current amount and current timestamp.
     {
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date();
    }

    public String getType() 
    {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

public class ATM {
    private double balance;
    private int pin;
    private List<Transaction> transactionHistory;// The new arraylist is created to store all the transactions.

    public ATM(double initialBalance, int initialPin) {
        balance = initialBalance;
        pin = initialPin;
        transactionHistory = new ArrayList<>();
    }

    public void credit(int amount, int enteredPin) // This function is used to store the deposited amount it only stores the amount when the entered pin is only valid.
    {
        if (isPinValid(enteredPin)) {
            balance += amount;
            Transaction transaction = new Transaction("Credit", amount);
            transactionHistory.add(transaction);
            System.out.println("Credited: " + amount);
        } else {
            System.out.println("Invalid PIN. Credit operation canceled.");
        }
    }

    public void debit(int amount, int enteredPin) // This method is used to withdraw the amount by checking the deposited amount from the account and it only gets debited when the entered pin is valid.
    {
        if (isPinValid(enteredPin)) {
            if (amount <= balance) {
                balance -= amount;
                Transaction transaction = new Transaction("Debit", amount);
                transactionHistory.add(transaction);
                System.out.println("Debited: " + amount);
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Invalid PIN. Debit operation canceled.");
        }
    }

    public void balanceEnquiry(int enteredPin) // This method is used to check the balance enquiry.
    {
        if (isPinValid(enteredPin)) {
            System.out.println("Balance: " + balance);
        } else {
            System.out.println("Invalid PIN. Balance enquiry operation canceled.");
        }
    }

    public void changePin(int newPin, int enteredPin) // This method is used to change the pin by using the current pin and it changes the pin.
    {
        if (isPinValid(enteredPin)) {
            pin = newPin;
            System.out.println("PIN changed successfully.");
        } else {
            System.out.println("Invalid PIN. PIN change operation canceled.");
        }
    }

    public boolean isPinValid(int enteredPin) {
        return enteredPin == pin;
    }

    public void printTransactionHistory(int enteredPin) // This method is used to call the transaction class methods and it tracks the whole transactions of the account.
    {
        if (isPinValid(enteredPin)) {
            System.out.println("\nTransaction History:");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");// This dateformat gives the current date and time of transaction.
            for (Transaction transaction : transactionHistory) {
                System.out.println("Type: " + transaction.getType());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Date and Time: " + dateFormat.format(transaction.getTimestamp()));
                System.out.println();
            }
        } else {
            System.out.println("Invalid PIN. Cannot view transaction history.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
		System.out.println("Insert ATM CARD.");
		System.out.println("Hi, please do not remove your card unit the whole transaction gets completed.");
        System.out.println("Enter the Initial Balance:");
        double initialBalance = scanner.nextDouble();

        int initialPin;
        do {
            System.out.println("Set your 4-digit PIN:");
            initialPin = scanner.nextInt();
        } while (initialPin < 1000 || initialPin > 9999);

        ATM atm = new ATM(initialBalance, initialPin);

        while (true) {
            System.out.println("\nATM Options:");
            System.out.println("1. Credit");
            System.out.println("2. Debit");
            System.out.println("3. Balance Enquiry");
            System.out.println("4. Change PIN");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            int enteredPin = -1;

            if (choice != 6) {
                System.out.print("Enter your PIN: ");
                enteredPin = scanner.nextInt();

                if (!atm.isPinValid(enteredPin)) {
                    System.out.println("Invalid PIN. Please try again.");
                    continue;
                }
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter the amount to credit: ");
                    int creditAmount = scanner.nextInt();
                    atm.credit(creditAmount, enteredPin);
                    break;
                case 2:
                    System.out.print("Enter the amount to debit: ");
                    int debitAmount = scanner.nextInt();
                    atm.debit(debitAmount, enteredPin);
                    break;
                case 3:
                    atm.balanceEnquiry(enteredPin);
                    break;
                case 4:
                    System.out.print("Enter your new 4-digit PIN: ");
                    int newPin = scanner.nextInt();
                    atm.changePin(newPin, enteredPin);
                    break;
                case 5:
                    atm.printTransactionHistory(enteredPin);
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
