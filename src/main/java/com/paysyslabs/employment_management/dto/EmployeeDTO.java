package com.paysyslabs.employment_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Data
@Builder
public class EmployeeDTO {

        private Long id;

        @NotBlank(message = "First name is required")
        private String firstName;

        @NotBlank(message = "Last name is required")
        private String lastName;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
        private String phone;

        @NotNull(message = "Date of joining is required")
        private LocalDate dateOfJoining;

        @NotNull(message = "Department ID is required")
        private Long departmentId;

        @NotNull(message = "Job Title ID is required")
        private Long jobTitleId;
}
