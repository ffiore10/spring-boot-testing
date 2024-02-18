package com.fiore.springboottesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiore.springboottesting.model.Employee;
import com.fiore.springboottesting.service.EmployeeService;
import com.fiore.springboottesting.service.RoseService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("ffiore@gmail.com")
                .build();

        BDDMockito.given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));  // risponderà con l'argomento passato nell'invocazione

        // when
        ResultActions response = mockMvc.perform(post("/api/employees")
//                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employee)))
//                    .andExpect(status().isOk())
                    ;

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
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

        BDDMockito.given(employeeService.getAllEmployees())
                .willReturn(employees);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
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


        BDDMockito.given(employeeService.getEmployeeById(any(Long.class)))
                .willReturn(Optional.of(employee));

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/1"));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
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


        BDDMockito.given(employeeService.getEmployeeById(any(Long.class)))
                .willReturn(Optional.empty());

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/1"));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
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



        BDDMockito.given(employeeService.getEmployeeById(Long.valueOf(1)))
                .willReturn(Optional.of(originalEmployee));
        BDDMockito.given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        // when
        ResultActions response = mockMvc.perform(put("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
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



        BDDMockito.given(employeeService.getEmployeeById(Long.valueOf(1)))
                .willReturn(Optional.empty());
//        BDDMockito.given(employeeService.updateEmployee(any(Employee.class)))
//                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        // when
        ResultActions response = mockMvc.perform(put("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                CoreMatchers.is(updatedEmployee.getFirstName())));
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.email",
//                CoreMatchers.is(updatedEmployee.getEmail())));
    }

    @Test
    public void givedEmployeeId_whenDeleteEmployee_returnDeletingNotification() throws Exception {

        // given
        BDDMockito.willDoNothing()
                .given(employeeService).deleteEmployee(ArgumentMatchers.any(Long.class));

        // when
        ResultActions response = mockMvc.perform(delete("/api/employees/1"));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }

    @Test
    public void givenSetup_whenSearchMaxIdAvailable_thenReturnLastEmployeeByIdIdPlusOne() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Francesco")
                .lastName("Fiore")
                .email("ffiore@gmail.com")
                .id(9L)
                .build();

        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@gmail.com")
                .id(10L)
                .build();

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employee2);

        BDDMockito.given(employeeService.retrieveLastEmployeeById())
                .willReturn(Optional.of(employee2));

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/retrieveIdAvailable"));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("11")); // Assuming the next available ID should be 11
    }
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                CoreMatchers.is(employee2.getFirstName())));
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.id",
//                CoreMatchers.is(employee2.getId()+1)));


}
