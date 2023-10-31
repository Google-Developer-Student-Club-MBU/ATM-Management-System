import java.util.Scanner;

class Account {
    String name;
    String accNumber;
    String accType;
    double balance;
    String[] transactions;
    int transactionCount;

    public Account() {
        transactions = new String[5];
        transactionCount = 0;
    }

    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("Deposited: " + amount);
            System.out.println("Deposit successful.");
        }
    }

    void displayBalance() {
        System.out.println("Account Balance: " + balance);
    }

    void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            addTransaction("Withdrawn: " + amount);
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient balance for withdrawal.");
        }
    }

    void addTransaction(String transaction) {
        if (transactionCount < 5) {
            transactions[transactionCount] = transaction;
            transactionCount++;
        } else {
            for (int i = 0; i < 4; i++) {
                transactions[i] = transactions[i + 1];
            }
            transactions[4] = transaction;
        }
    }

    void displayTransactions() {
        System.out.println("Recent Transactions:");
        for (int i = 0; i < transactionCount; i++) {
            System.out.println(transactions[i]);
        }
    }
}

class CurrAcct extends Account {
    void checkMinBalance(double minBalance) {
        if (balance < minBalance) {
            double penalty = balance * 0.01;  // 1% penalty
            balance -= penalty;
            addTransaction("Penalty imposed: " + penalty);
            System.out.println("Penalty imposed due to low balance.");
        }
    }
}

class SavAcct extends Account {
    void computeInterest(double rate) {
        double interest = balance * (rate / 100);
        balance += interest;
        addTransaction("Interest:" + interest);
    }
}

public class bankaccount{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account type (Savings/Current): ");
        String accType = scanner.next().toLowerCase();

        Account account;

        if (accType.equals("savings")) {
            account = new SavAcct();
            System.out.print("Enter interest rate: ");
            double rate = scanner.nextDouble();
            ((SavAcct) account).computeInterest(rate);
        } else if (accType.equals("current")) {
            account = new CurrAcct();
            System.out.print("Enter minimum balance: ");
            double minBalance = scanner.nextDouble();
            ((CurrAcct) account).checkMinBalance(minBalance);
        } else {
            System.out.println("Invalid account type.");
            return;
        }

        account.name = scanner.nextLine();
        System.out.print("Enter customer name: ");
        account.name = scanner.nextLine();
        System.out.print("Enter account number: ");
        account.accNumber = scanner.nextLine();
        account.accType = accType;

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Display Balance");
            System.out.println("4. Display Transactions");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    account.displayBalance();
                    break;
                case 4:
                    account.displayTransactions();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
