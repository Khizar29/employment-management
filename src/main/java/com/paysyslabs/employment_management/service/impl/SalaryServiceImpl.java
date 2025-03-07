package com.paysyslabs.employment_management.service.impl;

import com.paysyslabs.employment_management.dto.SalaryDTO;
import com.paysyslabs.employment_management.entity.Employee;
import com.paysyslabs.employment_management.entity.Salary;
import com.paysyslabs.employment_management.exception.EmployeeNotFoundException;
import com.paysyslabs.employment_management.repository.EmployeeRepository;
import com.paysyslabs.employment_management.repository.SalaryRepository;
import com.paysyslabs.employment_management.service.SalaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    public SalaryServiceImpl(SalaryRepository salaryRepository, EmployeeRepository employeeRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @Override
    public SalaryDTO createSalary(SalaryDTO salaryDTO) {
        Employee employee = employeeRepository.findById(salaryDTO.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        Salary salary = new Salary();
        salary.setBasicSalary(salaryDTO.getBasicSalary());
        salary.setBonus(salaryDTO.getBonus());
        salary.setEmployee(employee);

        Salary savedSalary = salaryRepository.save(salary);
        return new SalaryDTO(savedSalary.getId(), savedSalary.getBasicSalary(), savedSalary.getBonus(), savedSalary.getEmployee().getId());
    }

    @Override
    public SalaryDTO getSalaryByEmployeeId(Long employeeId) {
        Salary salary = salaryRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Salary not found for employee ID: " + employeeId));
        return new SalaryDTO(salary.getId(), salary.getBasicSalary(), salary.getBonus(), salary.getEmployee().getId());
    }

    @Override
    public SalaryDTO updateSalary(Long id, SalaryDTO salaryDTO) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary not found with ID: " + id));

        salary.setBasicSalary(salaryDTO.getBasicSalary());
        salary.setBonus(salaryDTO.getBonus());

        Salary updatedSalary = salaryRepository.save(salary);
        return mapToDTO(updatedSalary);
    }

    @Override
    public List<SalaryDTO> getAllSalaries() {
        return salaryRepository.findAll().stream()
                .map(salary -> new SalaryDTO(salary.getId(), salary.getBasicSalary(), salary.getBonus(), salary.getEmployee().getId()))
                .collect(Collectors.toList());
    }

    private SalaryDTO mapToDTO(Salary salary) {
        return new SalaryDTO(salary.getId(), salary.getBasicSalary(), salary.getBonus(), salary.getEmployee().getId());
    }
}

