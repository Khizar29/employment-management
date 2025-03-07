package com.paysyslabs.employment_management.controller;

import static com.paysyslabs.employment_management.constants.EmployeeEndpoints.*;

import com.paysyslabs.employment_management.dto.EmployeeDTO;
import com.paysyslabs.employment_management.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Employee", description = "APIs for managing employees")
@RestController
@RequestMapping(BASE)
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(CREATE)
    @Operation(summary = "Create a new employee", description = "Saves a new employee in the system")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok(createdEmployee);
    }

    @GetMapping(GET_ALL)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all employees", description = "Fetches all employees from the system")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        logger.info("Fetching all employees...");
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        logger.info("Successfully fetched {} employees", employees.size());
        return ResponseEntity.ok(employees);
    }

    // ✅ Get Employee by ID
    @GetMapping(GET_BY_ID)
    @Operation(summary = "Get employee by ID", description = "Fetches a specific employee based on ID")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping(UPDATE)
    @Operation(summary = "Update employee details", description = "Updates an existing employee’s information")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping(DELETE)
    @Operation(summary = "Delete employee", description = "Removes an employee from the system")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
