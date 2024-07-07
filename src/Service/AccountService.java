package Service;

import Entity.Account;
import Entity.Curency;
import Entity.Customer;
import Generic.IGeneric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountService implements IGeneric<Account> {
    private List<Account> accounts = new ArrayList<>();
    private List<Customer> customers;
    @Override
    public void displayEntities(List<Account> accounts) {
        accounts.stream().forEach(System.out::println);
    }

    public List<Account> getAllAccounts(){
        return accounts;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public void loadAccounts(String filePath){
        int cusId;
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line=bufferedReader.readLine())!=null){
                if(line.length()>0){
                    Account acc=new Account();
                    String accinfo[]=line.split(";");
                    acc.setId(Integer.parseInt(accinfo[0]));
                    cusId=Integer.parseInt(accinfo[1]);
                    acc.setCustomer(cusId,customers);
                    acc.setBalance(Double.parseDouble(accinfo[2]));
                    acc.setCurency(Curency.valueOf(accinfo[3]));
                    accounts.add(acc);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
