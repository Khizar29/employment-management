package com.paysyslabs.employment_management.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Hello, Admin!";
    }

    @GetMapping("/hr")
    @PreAuthorize("hasRole('HR')")
    public String hrAccess() {
        return "Hello, HR!";
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String employeeAccess() {
        return "Hello, Employee!";
    }

    @GetMapping("/public")
    public String publicAccess() {
        return "Hello, Public!";
    }
}
