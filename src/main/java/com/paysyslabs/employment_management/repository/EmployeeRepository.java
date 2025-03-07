package com.paysyslabs.employment_management.repository;

import com.paysyslabs.employment_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EmployeeRepository  extends JpaRepository <Employee, Long> {

    Optional <Employee> findByEmail (String email);
}
