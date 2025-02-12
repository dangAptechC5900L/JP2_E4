package Entity;

import java.time.LocalDateTime;
import java.util.List;

public class Transaction {
    private int id;
    private Account account;
    private double amount;
    private Type type;
    private LocalDateTime dateTime;
    private Status status;

    public Transaction(){;}

    public Transaction(int id, Account account, double amount, Type type, LocalDateTime dateTime, Status status) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.type = type;
        this.dateTime = dateTime;
        this.status=status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account accountId) {
        this.account = accountId;
    }

    public void setAccount(int accountId, List<Account> accounts){
        for (Account account:accounts){
            if(account.getId()==accountId){
                this.account=account;
                break;
            }
        }
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", account=" + account +
                ", amount=" + amount +
                ", type=" + type +
                ", dateTime=" + dateTime +
                ", status=" + status +
                '}';
    }
}
