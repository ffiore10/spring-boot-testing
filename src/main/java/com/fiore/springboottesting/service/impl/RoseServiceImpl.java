package com.fiore.springboottesting.service.impl;

import com.fiore.springboottesting.exception.EmployeeNotFoundException;
import com.fiore.springboottesting.model.Employee;
import com.fiore.springboottesting.model.Rose;
import com.fiore.springboottesting.repository.EmployeeRepository;
import com.fiore.springboottesting.service.EmployeeService;
import com.fiore.springboottesting.service.RoseService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RoseServiceImpl implements RoseService {

//    private EmployeeRepository employeeRepository;

//    public RoseServiceImpl(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }
//
//    public EmployeeServiceImpl employeeService(EmployeeRepository employeeRepository){
//        this.employeeRepository = employeeRepository;
//        return this;
//    }

    @Override
    public List<Rose> getAllRose() {
        return Arrays.asList(
                new Rose(1, "A.S.Longobarda", "Francesco", 35),
                new Rose(2, "Boca Juana", "Federico", 35),
                new Rose(3, "Namerda", "Paolo", 33),
                new Rose(4, "Pescaramanzia", "Michele", 31),
                new Rose(5, "Feralpisano", "Marco", 27),
                new Rose(6, "Nderr La Lanze", "Giovanni", 20)

        );
    }


}
