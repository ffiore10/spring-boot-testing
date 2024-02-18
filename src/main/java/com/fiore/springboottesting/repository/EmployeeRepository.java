package com.fiore.springboottesting.repository;

import com.fiore.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByFirstNameAndEmail(String firstName, String email);

    // TODO
    // write method custom query using JPQL with index and named parameters
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Optional<Employee> findByJPQL(String firstName, String lastName);

    // TODO
    // write method custom query using native query with index and named parameters
    @Query(value = "select * from employees e where e.first_name = :firstname and e.last_name = :lastname", nativeQuery = true)
    Optional<Employee> findByCustomQuerySQL(@Param("firstname") String firstName,@Param("lastname") String lastName);

    Optional<Employee> findTopByOrderByIdDesc();
}
