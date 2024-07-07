package Entity;

import java.util.List;

public class Account {
    private int id;
    private Customer customer;
    private double balance;
    private Curency curency;

   public Account(){;}

    public Account(int id, Customer customer, double balance, Curency curency) {
        this.id = id;
        this.customer = customer;
        this.balance = balance;
        this.curency = curency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCustomer(int customerId, List<Customer> customers) {
       for(Customer customer:customers){
           if(customer.getId()==customerId){
               this.customer=customer;
               break;
           }
       }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Curency getCurency() {
        return curency;
    }

    public void setCurency(Curency curency) {
        this.curency = curency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customer=" + customer +
                ", balance=" + balance +
                ", curency=" + curency +
                '}';
    }
}
