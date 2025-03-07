package com.paysyslabs.employment_management.controller;

import com.paysyslabs.employment_management.constants.SalaryEndpoints;
import com.paysyslabs.employment_management.dto.SalaryDTO;
import com.paysyslabs.employment_management.service.SalaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Salary", description = "APIs for managing salaries")
@RestController
@RequestMapping(SalaryEndpoints.BASE)
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping(SalaryEndpoints.CREATE)
    @Operation(summary = "Create a new salary", description = "Saves salary details for an employee")
    public ResponseEntity<SalaryDTO> createSalary(@Valid @RequestBody SalaryDTO salaryDTO) {
        SalaryDTO createdSalary = salaryService.createSalary(salaryDTO);
        return ResponseEntity.ok(createdSalary);
    }

    @GetMapping(SalaryEndpoints.GET_BY_EMPLOYEE_ID)
    @Operation(summary = "Get salary by employee ID", description = "Fetch salary details of a specific employee")
    public ResponseEntity<SalaryDTO> getSalaryByEmployeeId(@PathVariable Long employeeId) {
        SalaryDTO salaryDTO = salaryService.getSalaryByEmployeeId(employeeId);
        return ResponseEntity.ok(salaryDTO);
    }

    @PutMapping(SalaryEndpoints.UPDATE)
    @Operation(summary = "Update salary", description = "Update salary details for an employee")
    public ResponseEntity<SalaryDTO> updateSalary(@PathVariable Long id, @RequestBody SalaryDTO salaryDTO) {
        SalaryDTO updatedSalary = salaryService.updateSalary(id, salaryDTO);
        return ResponseEntity.ok(updatedSalary);
    }

    @GetMapping(SalaryEndpoints.GET_ALL)
    @Operation(summary = "Get all salaries", description = "Fetches all salaries in the system")
    public ResponseEntity<List<SalaryDTO>> getAllSalaries() {
        return ResponseEntity.ok(salaryService.getAllSalaries());
    }
}
