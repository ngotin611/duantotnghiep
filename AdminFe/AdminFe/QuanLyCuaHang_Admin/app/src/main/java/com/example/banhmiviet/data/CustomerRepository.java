package com.example.banhmiviet.data;

import com.example.banhmiviet.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private static CustomerRepository instance;
    private final List<Customer> customers = new ArrayList<>();

    private CustomerRepository() {
        mockData();
    }

    public static CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    private void mockData() {
        customers.add(new Customer("C01", "Hoàng", "0909 999 999", 5, 350000));
        customers.add(new Customer("C02", "Lan", "0904 444 444", 2, 120000));
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public int getTotalCustomerCount() {
        return customers.size();
    }
}
