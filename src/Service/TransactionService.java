package Service;

import Entity.*;
import Generic.IGeneric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionService implements IGeneric<Transaction> {
    private List<Transaction> transactions= new ArrayList<Transaction>();
    private List<Account> accounts;

    @Override
    public void displayEntities(List<Transaction> transactions) {
        transactions.stream().forEach(transaction -> System.out.println(formatTransaction(transaction)));
    }

    // Phương thức để định dạng thông tin giao dịch
    private String formatTransaction(Transaction transaction) {
        return String.format("Transaction{id=%d, account=Account{id=%d, customer=Customer{id=%d, name='%s', phone='%s'}, balance=%s, currency=%s}, amount=%s, type=%s, dateTime=%s, status=%s}",
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getAccount().getCustomer().getId(),
                transaction.getAccount().getCustomer().getName(),
                transaction.getAccount().getCustomer().getPhone(),
                formatBalance(transaction.getAccount().getBalance()),
                transaction.getAccount().getCurency(),
                formatBalance(transaction.getAmount()),
                transaction.getType(),
                transaction.getDateTime(),
                transaction.getStatus());
    }

    public List<Transaction> getAllTransactions(){
        return transactions;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void loadTransaction(String filePath) {
        int accId;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() > 0) {
                    Transaction tr = new Transaction();
                    String[] trinfo = line.split(";");
                    tr.setId(Integer.parseInt(trinfo[0]));
                    accId = Integer.parseInt(trinfo[1]);
                    tr.setAccount(accId, accounts);
                    tr.setAmount(Double.parseDouble(trinfo[2]));
                    try {
                        tr.setType(Type.valueOf(trinfo[3].trim().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Loại giao dịch không hợp lệ: " + trinfo[3]);
                        continue;
                    }

                    tr.setDateTime(LocalDateTime.parse(trinfo[4], formatter)); // Parse ngay từ chuỗi không cần điều chỉnh

                    try {
                        tr.setStatus(Status.valueOf(trinfo[5].trim().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Trạng thái không hợp lệ: " + trinfo[5]);
                        continue; // Bỏ qua giao dịch này nếu trạng thái không hợp lệ
                    }
                    transactions.add(tr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để định dạng số dư
    private String formatBalance(double balance) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(balance);
    }



    public Optional<Transaction> withdraw(int accountId,double amount){
        if(amount%10!=0){
            System.out.println("Amount must be a multiple of 10.");
            return Optional.empty();
        }
        return accounts.stream()
                .filter(account -> account.getId()== accountId)
                .findFirst()
                .filter(account -> account.getBalance()>=amount)
                .map(account -> {
                   account.setBalance(account.getBalance() - amount);
                   Transaction transaction=new Transaction();
                   transaction.setId(transactions.size()+1); //đặt giá trị id giao dịch mới
                   transaction.setAccount(accountId,accounts);
                   transaction.setAmount(amount); //số tiền giao dịch
                   transaction.setType(Type.WITHDRAWAL);
                   transaction.setStatus(Status.C);
                   transaction.setDateTime(LocalDateTime.now());
                    transactions.add(transaction);
                    return transaction;

                });

    }

    public Optional<Double> checkBalance(int accountId) {
        return accounts.stream()
                .filter(account -> account.getId() == accountId)
                .map(Account::getBalance)
                .findFirst();
    }




}
