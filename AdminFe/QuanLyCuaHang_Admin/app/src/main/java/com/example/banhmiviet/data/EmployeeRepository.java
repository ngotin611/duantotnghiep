package com.example.banhmiviet.data;

import com.example.banhmiviet.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private static EmployeeRepository instance;
    private final List<Employee> employees = new ArrayList<>();

    private EmployeeRepository() {
        mockData();
    }

    public static EmployeeRepository getInstance() {
        if (instance == null) {
            instance = new EmployeeRepository();
        }
        return instance;
    }

    private void mockData() {
        employees.add(new Employee("E01", "Nguyễn Văn A", "Quản lý",
                "0901 111 111", "a@sapo.com", 28, "Nam"));

        employees.add(new Employee("E02", "Trần Thị B", "Thu ngân",
                "0902 222 222", "b@sapo.com", 25, "Nữ"));

        employees.add(new Employee("E03", "Lê Văn C", "Bán hàng",
                "0903 333 333", "c@sapo.com", 30, "Nam"));
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public int getTotalEmployeeCount() {
        return employees.size();
    }
}
