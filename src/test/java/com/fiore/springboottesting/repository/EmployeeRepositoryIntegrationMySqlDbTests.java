package com.fiore.springboottesting.repository;

import com.fiore.springboottesting.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // con questa annotation stiamo dicendo di non leggere l'autoconfigurazione che si
// porta dietro DataJpaTest, che configura un h2 in memory database, ma gli stiamo dicendo di leggere una configurazione da noi impostata
// ora quindi leggerà la configurazione di mySqlDb configurata nell'application_conf.properties
@Slf4j
public class EmployeeRepositoryIntegrationMySqlDbTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup(){
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Francesco")
                .lastName("Francesco")
                .email("ffiore@idea.com")
                .build();

        Employee employee3 = Employee.builder()
                .firstName("The Rock")
                .lastName("Dwayne")
                .email("dwayene@idea.com")
                .build();
        Employee savedEmployee1 = employeeRepository.save(employee);
        employeeRepository.save(employee3);
    }

    @Test
    public void givenSetup_whenFindByJPQL_thenReturnEmployee(){

        //given

        //when
        Optional<Employee> employeeDB = employeeRepository.findByJPQL("Francesco", "Francesco");
        assertThat(employeeDB).isPresent();
        assertThat(employeeDB.get().getEmail()).isEqualTo("ffiore@idea.com");
    }

    @Test
    public void givenSetup_whenFindBySQLNativeQuery_thenReturnEmployee(){

        //given

        //when
        Optional<Employee> employeeDB = employeeRepository.findByCustomQuerySQL("Francesco", "Francesco");
        assertThat(employeeDB).isPresent();
        assertThat(employeeDB.get().getEmail()).isEqualTo("ffiore@idea.com");
    }

    @Test
//    @DisplayName("junit test for save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Umberto")
                .lastName("Umberto")
                .email("ffiore@idea.com")
                .build();

        // when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
        assertThat(savedEmployee.getFirstName()).isEqualTo("Umberto");

    }

    @Test
    public void givenEmployeeObjectSaved_whenFindByEmail_thenReturnSavedEmployee(){
        // given - precondition or setup

        // when - action or the behaviour that we are going to test
        Employee employeeDb = employeeRepository.findByEmail("ffiore@idea.com").get();
        // then - verify the output
        assertThat(employeeDb).isNotNull();
        assertThat(employeeDb.getId()).isGreaterThan(0);
        assertThat(employeeDb.getFirstName()).isEqualTo("Francesco");

    }

    @Test
    public void givenEmployeeObjectSaved_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Francesco")
//                .lastName("Francesco")
//                .email("ffiore@idea.com")
//                .build();
//
//        Employee employee3 = Employee.builder()
//                .firstName("The Rock")
//                .lastName("Dwayne")
//                .email("dwayene@idea.com")
//                .build();
//        Employee savedEmployee1 = employeeRepository.save(employee);
//        employeeRepository.save(employee3);

        // when - action or the behaviour that we are going to test
        Employee employeeDb = employeeRepository.findByEmail("ffiore@idea.com").get();
        employeeDb.setEmail("francesco@idea.com");
        Employee finalEmployee1 = employeeRepository.save(employeeDb);
        // then - verify the output
        assertThat(finalEmployee1).isNotNull();
        assertThat(finalEmployee1.getEmail()).isEqualTo("francesco@idea.com");
    }

    @Test
    public void givenSavedEmploy_whenDelete_thenRemoveEmployee(){

        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Francesco")
                .lastName("Francesco")
                .email("ffiore@idea.com")
                .build();

        Employee employee3 = Employee.builder()
                .firstName("The Rock")
                .lastName("Dwayne")
                .email("dwayene@idea.com")
                .build();
        Employee savedEmployee1 = employeeRepository.save(employee);
        employeeRepository.save(employee3);
        // when - action or the behaviour that we are going to test
        employeeRepository.deleteById(savedEmployee1.getId());

        // then - verify the output
        List<Employee> allEmployees = employeeRepository.findAll();
        assertThat(allEmployees.size()).isEqualTo(4);

        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        assertThat(deletedEmployee).isNotPresent(); // or assertThat(deletedEmployee).isEmpty();
    }

    @Test
    public void givenSetup_whenSearchFirstIdAvailable_returnLastEmployeeIdPlusOne(){

        // given
        Employee employee = Employee.builder()
                .firstName("Francesco")
                .lastName("Francesco")
                .email("ffiore@idea.com")
                .build();

        Employee employee3 = Employee.builder()
                .firstName("The Rock")
                .lastName("Dwayne")
                .email("dwayene@idea.com")
                .build();
        // when
        List<Employee> employees = employeeRepository.findAll();
        long maxId = employees.stream()
                .max((empl1,empl2) -> Long.compare(empl1.getId(), empl2.getId())).get().getId();
        Optional<Employee> maxEmployeeById = employeeRepository.findTopByOrderByIdDesc();
        // then
        assertThat(maxEmployeeById.get().getId()).isEqualTo(maxId);
    }

    //
}
