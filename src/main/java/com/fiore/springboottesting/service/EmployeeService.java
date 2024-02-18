package com.fiore.springboottesting.service;

import com.fiore.springboottesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public Employee saveEmployee(Employee employee);
    public List<Employee> getAllEmployees();

    public Optional<Employee> getEmployeeById(Long id);

    Employee updateEmployee(Employee employee);

    public void deleteEmployee(long id);

    public Optional<Employee> retrieveLastEmployeeById();

}
