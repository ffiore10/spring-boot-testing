package com.fiore.springboottesting.controller;

import com.fiore.springboottesting.model.Employee;
import com.fiore.springboottesting.model.Rose;
import com.fiore.springboottesting.service.RoseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rose")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RoseController {

    private RoseService roseService;

    // con l'annotation AllArgsConstructor spring costruisce un costruttore con tutti gli attributi della classe e quindi non è più necessario quanto seguee
//    public EmployeeController(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Employee createEmployee(@RequestBody Employee employee){
//        return employeeService.saveEmployee(employee);
//    }

    @GetMapping
    public List<Rose> getAllRose(){
        return roseService.getAllRose();
    }

//    @GetMapping("{id}")
//    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId){
//        return employeeService.getEmployeeById(employeeId)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("{id}")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeId,
//                                                   @RequestBody Employee employee) {
//        return employeeService.getEmployeeById(employeeId)
//                .map(savedEmployee -> {
//                    savedEmployee.setFirstName(employee.getFirstName());
//                    savedEmployee.setLastName(employee.getLastName());
//                    savedEmployee.setEmail(employee.getEmail());
//                    Employee updatedEmploy = employeeService.updateEmployee(savedEmployee);
//                    return new ResponseEntity<>(updatedEmploy, HttpStatus.OK);
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<String> deleteEmploy(@PathVariable("id") long employeeId) {
//        employeeService.deleteEmployee(employeeId);
//        return new ResponseEntity<>("Employee deleted successfully!", HttpStatus.OK);
//    }
}
