package com.paysyslabs.employment_management.service.impl;

import com.paysyslabs.employment_management.dto.DepartmentDTO;
import com.paysyslabs.employment_management.dto.SalaryDTO;
import com.paysyslabs.employment_management.entity.Attendance;
import com.paysyslabs.employment_management.entity.Department;
import com.paysyslabs.employment_management.entity.Salary;
import com.paysyslabs.employment_management.exception.DepartmentNotFoundException;
import com.paysyslabs.employment_management.repository.DepartmentRepository;
import com.paysyslabs.employment_management.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    @Autowired
    public  DepartmentServiceImpl (DepartmentRepository departmentRepository)
    {
        this.departmentRepository= departmentRepository;
    }


    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        Department savedDepartment = departmentRepository.save(department);

        // 🔍 Debugging - Check if ID is being set
        logger.info("Saved Department: ID={}, Name={}", savedDepartment.getId(), savedDepartment.getName());

        return new DepartmentDTO(savedDepartment.getId(), savedDepartment.getName());
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(department -> new DepartmentDTO(
                        department.getId(),
                        department.getName()
                )).toList();
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        return new DepartmentDTO(department.getId(), department.getName());
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));

        department.setName(departmentDTO.getName());


        Department updatedDepartment = departmentRepository.save(department);
        return mapToDTO(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        departmentRepository.delete(department);
    }

    private DepartmentDTO mapToDTO(Department department) {
        return new DepartmentDTO(department.getId(), department.getName());
    }
}
