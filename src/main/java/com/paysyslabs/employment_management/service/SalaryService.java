package com.paysyslabs.employment_management.service;

import com.paysyslabs.employment_management.dto.SalaryDTO;
import java.util.List;

public interface SalaryService {
    SalaryDTO createSalary(SalaryDTO salaryDTO);
    SalaryDTO getSalaryByEmployeeId(Long employeeId);
    List<SalaryDTO> getAllSalaries();
    SalaryDTO updateSalary(Long id, SalaryDTO salaryDTO);
}
