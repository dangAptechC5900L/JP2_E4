package Service;

import Entity.Customer;
import Generic.IGeneric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService implements IGeneric<Customer> {
    private List<Customer> customers = new ArrayList<>();
    @Override
    public void displayEntities(List<Customer> customers) {
       customers.stream().forEach(System.out::println);
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public void loadCustomers(String filePath) {
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() > 0) {
                    Customer cs = new Customer();
                    String[] cusinfo = line.split(";");
                    cs.setId(Integer.parseInt(cusinfo[0]));
                    cs.setName(cusinfo[1]);
                    cs.setPhone(cusinfo[2]);
                    customers.add(cs);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
