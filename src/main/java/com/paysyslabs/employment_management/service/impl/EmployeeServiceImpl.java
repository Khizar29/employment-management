package com.paysyslabs.employment_management.service.impl;

import com.paysyslabs.employment_management.dto.EmployeeDTO;
import com.paysyslabs.employment_management.entity.Department;
import com.paysyslabs.employment_management.entity.Employee;
import com.paysyslabs.employment_management.entity.JobTitle;
import com.paysyslabs.employment_management.exception.ResourceNotFoundException;
import com.paysyslabs.employment_management.repository.DepartmentRepository;
import com.paysyslabs.employment_management.repository.EmployeeRepository;
import com.paysyslabs.employment_management.repository.JobTitleRepository;
import com.paysyslabs.employment_management.service.EmployeeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobTitleRepository jobTitleRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               DepartmentRepository departmentRepository,
                               JobTitleRepository jobTitleRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.jobTitleRepository = jobTitleRepository;
    }

    @Transactional
    @Override
    public EmployeeDTO createEmployee(@Valid EmployeeDTO employeeDTO) {
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        JobTitle jobTitle = jobTitleRepository.findById(employeeDTO.getJobTitleId())
                .orElseThrow(() -> new ResourceNotFoundException("Job title not found"));

        Employee employee = Employee.builder()
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .email(employeeDTO.getEmail())
                .phone(employeeDTO.getPhone())
                .dateOfJoining(employeeDTO.getDateOfJoining())
                .department(department)
                .jobTitle(jobTitle)
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToDTO(savedEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return mapToDTO(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, @Valid EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Update only non-null fields
        if (employeeDTO.getFirstName() != null) employee.setFirstName(employeeDTO.getFirstName());
        if (employeeDTO.getLastName() != null) employee.setLastName(employeeDTO.getLastName());
        if (employeeDTO.getEmail() != null) employee.setEmail(employeeDTO.getEmail());
        if (employeeDTO.getPhone() != null) employee.setPhone(employeeDTO.getPhone());
        if (employeeDTO.getDateOfJoining() != null) employee.setDateOfJoining(employeeDTO.getDateOfJoining());

        // Update Department if provided
        if (employeeDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            employee.setDepartment(department);
        }

        // Update Job Title if provided
        if (employeeDTO.getJobTitleId() != null) {
            JobTitle jobTitle = jobTitleRepository.findById(employeeDTO.getJobTitleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Job title not found"));
            employee.setJobTitle(jobTitle);
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employeeRepository.delete(employee);
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .dateOfJoining(employee.getDateOfJoining())
                .departmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null)
                .jobTitleId(employee.getJobTitle() != null ? employee.getJobTitle().getId() : null)
                .build();
    }
}
