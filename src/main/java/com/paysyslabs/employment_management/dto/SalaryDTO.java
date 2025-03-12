package com.paysyslabs.employment_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDTO {
    private Long id;

    @NotNull(message = "Basic salary is required")
    @Min(value = 0, message = "Basic salary must be a positive value")
    private BigDecimal basicSalary;

    private BigDecimal bonus = BigDecimal.ZERO;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;
}
