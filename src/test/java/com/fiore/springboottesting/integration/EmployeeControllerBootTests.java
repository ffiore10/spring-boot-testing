package com.fiore.springboottesting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiore.springboottesting.model.Employee;
import com.fiore.springboottesting.repository.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerBootTests {
    // this is the integration tests class

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("ffiore@gmail.com")
                .build();

        // when
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                CoreMatchers.is(employee.getFirstName())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                CoreMatchers.is(employee.getLastName())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.email",
                CoreMatchers.is(employee.getEmail())));
    }

    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnListEmployee() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("ffiore@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@gmail.com")
                .build();

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employee2);

        employeeRepository.saveAll(employees);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                CoreMatchers.is(2)));
    }

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("ffiore@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                CoreMatchers.is(employee.getFirstName())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                CoreMatchers.is(employee.getLastName())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.email",
                CoreMatchers.is(employee.getEmail())));
    }

    @Test
    public void givenEmployeeIdNotExisting_whenGetEmployeeById_thenReturnNothing() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("ffiore@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/1522"));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void givenEmployeeToUpdate_whenUpdateEmployee_thenReturnEmployee() throws Exception {
        // given
        Employee originalEmployee = Employee.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("flower@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Flower")
                .lastName("Fiore")
                .email("flower@gmail.com")
                .build();



        employeeRepository.save(originalEmployee);

        // when
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", originalEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                CoreMatchers.is(updatedEmployee.getFirstName())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.email",
                CoreMatchers.is(updatedEmployee.getEmail())));
    }

    @Test
    public void givenEmployeeToUpdate_whenUpdateEmployee_thenReturnError() throws Exception {
        // given
        Employee originalEmployee = Employee.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("flower@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Flower")
                .lastName("Fiore")
                .email("flower@gmail.com")
                .build();

        employeeRepository.save(originalEmployee);


        // when
        ResultActions response = mockMvc.perform(put("/api/employees/231")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                CoreMatchers.is(updatedEmployee.getFirstName())));
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.email",
//                CoreMatchers.is(updatedEmployee.getEmail())));
    }

    @Test
    public void givedEmployeeId_whenDeleteEmployee_returnDeletingNotification() throws Exception {

        // given
        Employee originalEmployee = Employee.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("flower@gmail.com")
                .build();
        employeeRepository.save(originalEmployee);

        // when
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", originalEmployee.getId()));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
