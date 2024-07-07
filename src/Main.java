import Entity.*;
import Service.AccountService;
import Service.CustomerService;
import Service.TransactionService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Period;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        int cusId,accId;
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        List<Customer> customers=new ArrayList<Customer>();
        List<Account> accounts=new ArrayList<Account>();
        List<Transaction> transactions=new ArrayList<Transaction>();

        //Step 1: Get Ralative Path of File
        String rootPath=System.getProperty("user.dir");
        String fileCustomerPath=rootPath.replace("/","\\") + "\\data\\Customer.txt";
        String fileOutPath=rootPath.replace("/","\\") +"\\data\\data.out";
        String fileAccountPath=rootPath.replace("/","\\") +"\\data\\Account.txt";
        String fileTransactionPath=rootPath.replace("/","\\") +"\\data\\Transaction.txt";

        CustomerService customerService=new CustomerService();
        customerService.loadCustomers(fileCustomerPath);

        // Display loaded customers
        customers = customerService.getAllCustomers();
        customerService.displayEntities(customers);

        AccountService accountService=new AccountService();
        accountService.setCustomers(customers);
        accountService.loadAccounts(fileAccountPath);

        accounts=accountService.getAllAccounts();
        accountService.displayEntities(accounts);

        TransactionService transactionService=new TransactionService();
        transactionService.setAccounts(accounts);
        transactionService.loadTransaction(fileTransactionPath);

        transactions=transactionService.getAllTransactions();
        transactionService.displayEntities(transactions);

        Scanner scanner = new Scanner(System.in);

        // Thực hiện rút tiền
        System.out.println("Enter account ID to withdraw from:");
        int accountId = scanner.nextInt();
        System.out.println("Enter amount to withdraw:");
        double amount = scanner.nextDouble();
        Optional<Transaction> transaction = transactionService.withdraw(accountId, amount);
        if (transaction.isPresent()) {
            System.out.println("Transaction completed: " + transaction.get());
        } else {
            System.out.println("Transaction failed.");
        }


        // Thực hiện truy vấn số dư
        System.out.println("Enter account ID to check balance:");
        accountId = scanner.nextInt();
        Optional<Double> balance = transactionService.checkBalance(accountId);
        if (balance.isPresent()) {
            System.out.println("Current balance: " + balance.get());
        } else {
            System.out.println("Account not found.");
        }



    }
}
