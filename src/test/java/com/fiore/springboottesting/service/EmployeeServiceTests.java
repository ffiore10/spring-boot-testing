package com.fiore.springboottesting.service;

import com.fiore.springboottesting.exception.EmployeeNotFoundException;
import com.fiore.springboottesting.model.Employee;
import com.fiore.springboottesting.repository.EmployeeRepository;
import com.fiore.springboottesting.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("ffiore@gmail.com")
                .id(1L)
                .build();

    }

    @Test
    public void givenEmployee_whenSave_thenReturnSavedEmployee(){

        // given
        // mock with BDDMockito
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // mock with mockito mock standard way
//        Mockito.when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
//        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isEqualTo(1L);
    }

    @Test
    public void givenEmployee_whenSave_thenThrowsException(){
        // TODO da rivedere
        Employee savedEmployee = null;
        // given
        // mock with BDDMockito
        given(employeeRepository.findByEmail(employee.getEmail())).willThrow(new EmployeeNotFoundException("Employee already exists with " + employee.getEmail()));
//        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
//        given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
//        assertThat(savedEmployee).isNull();
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void givenEmployees_whenFindAllEmployees_thenReturnEmployeesList(){
        Employee employee2 = Employee.builder()
                .firstName("Umberto")
                .lastName("Fiore")
                .email("fioumberto@gmail.com")
                .id(2L)
                .build();

        // given
        // mock with BDDMockito
//        given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));

        Mockito.when(employeeRepository.findAll()).thenReturn(List.of(employee, employee2));

        // when
        List<Employee> allEmployees = employeeService.getAllEmployees();

        // then
        assertThat(allEmployees).isNotNull();
        assertThat(allEmployees.size()).isEqualTo(2);
    }

    @Test
    public void givenEmployees_whenFindAllEmployees_thenReturnEmployeesListNegativeScenario(){

        // given
        // mock with BDDMockito
        given(employeeRepository.findAll()).willReturn(List.of());


        // when
        List<Employee> allEmployees = employeeService.getAllEmployees();

        // then
        assertThat(allEmployees).isEmpty();
    }

    @Test
    public void givenEmployeeId_whenSearchById_thenReturnSavedEmployee(){

        // given
        // mock with BDDMockito
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        // when
        Optional<Employee> savedEmployee = employeeService.getEmployeeById(employee.getId());

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.get().getId()).isEqualTo(1L);
        assertThat(savedEmployee.get().getFirstName()).isEqualTo("Francesco");
    }

    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee(){

        // given
        // mock with BDDMockito
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("kaka@gmail.com");

        // when
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getId()).isEqualTo(1L);
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Francesco");
        assertThat(updatedEmployee.getEmail()).isEqualTo("kaka@gmail.com");
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing(){

        // given
        // mock with BDDMockito
        willDoNothing().given(employeeRepository).deleteById(employee.getId());

        // when
        employeeService.deleteEmployee(employee.getId());

        // then
        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }

}
