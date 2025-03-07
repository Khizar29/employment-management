package com.paysyslabs.employment_management.dto;

import com.paysyslabs.employment_management.entity.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {private Long id;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Check-in time is required")
    private LocalTime checkInTime;

    @NotNull(message = "Check-out time is required")
    private LocalTime checkOutTime;

    @NotNull(message = "Attendance status is required")
    private AttendanceStatus status;
}
