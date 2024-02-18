package com.fiore.springboottesting.service.impl;

import com.fiore.springboottesting.exception.EmployeeNotFoundException;
import com.fiore.springboottesting.model.Employee;
import com.fiore.springboottesting.repository.EmployeeRepository;
import com.fiore.springboottesting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
//
//    public EmployeeServiceImpl employeeService(EmployeeRepository employeeRepository){
//        this.employeeRepository = employeeRepository;
//        return this;
//    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmploy = employeeRepository.findByEmail(employee.getEmail());
        if(!savedEmploy.isEmpty()){
            throw new EmployeeNotFoundException("Employee already exists with mail: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Optional<Employee> retrieveLastEmployeeById() {
        return employeeRepository.findTopByOrderByIdDesc();
    }
}
