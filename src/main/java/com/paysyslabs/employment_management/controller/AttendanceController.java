package com.paysyslabs.employment_management.controller;

import com.paysyslabs.employment_management.constants.AttendanceEndpoints;
import com.paysyslabs.employment_management.dto.AttendanceDTO;
import com.paysyslabs.employment_management.service.AttendanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Attendance", description = "APIs for managing attendances")
@RestController
    @RequestMapping(AttendanceEndpoints.BASE)
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping(AttendanceEndpoints.CREATE)
    public ResponseEntity<AttendanceDTO> createAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        return ResponseEntity.ok(attendanceService.createAttendance(attendanceDTO));
    }

    @GetMapping(AttendanceEndpoints.GET_ALL)
    public ResponseEntity <List<AttendanceDTO>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }
    @GetMapping(AttendanceEndpoints.GET_BY_ID)
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

    @GetMapping(AttendanceEndpoints.GET_BY_EMPLOYEE_ID)
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployeeId(employeeId));
    }

    @PutMapping(AttendanceEndpoints.UPDATE)
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable Long id, @RequestBody AttendanceDTO attendanceDTO) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, attendanceDTO));
    }

    @DeleteMapping(AttendanceEndpoints.DELETE)
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }

}
