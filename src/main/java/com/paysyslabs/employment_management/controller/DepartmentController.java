package com.paysyslabs.employment_management.controller;

import com.paysyslabs.employment_management.constants.AttendanceEndpoints;
import com.paysyslabs.employment_management.constants.DepartmentEndpoints;
import com.paysyslabs.employment_management.dto.AttendanceDTO;
import com.paysyslabs.employment_management.dto.DepartmentDTO;
import com.paysyslabs.employment_management.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Department", description = "APIs for managing departments")
@RestController
@RequestMapping(DepartmentEndpoints.BASE)
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController (DepartmentService departmentService)
    {this.departmentService= departmentService;}

    @PostMapping(DepartmentEndpoints.CREATE)
    @Operation(summary = "Create a new department", description = "Saves a new department in the system")
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);
        return ResponseEntity.ok(createdDepartment);
    }
    @GetMapping(DepartmentEndpoints.GET_ALL)
    @Operation(summary = "Get all departments", description = "Fetches all departments from the system")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping(DepartmentEndpoints.GET_BY_ID)
    @Operation(summary = "Get department by ID", description = "Fetches a department by its ID")
    public ResponseEntity<DepartmentDTO> findDepartmentById(@PathVariable Long id) {
        DepartmentDTO department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }
    @PutMapping(DepartmentEndpoints.UPDATE)
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDTO));
    }

    @DeleteMapping(DepartmentEndpoints.DELETE)
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
