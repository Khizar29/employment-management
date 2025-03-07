package com.paysyslabs.employment_management.repository;

import com.paysyslabs.employment_management.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByEmployeeId(Long employeeId);
}
