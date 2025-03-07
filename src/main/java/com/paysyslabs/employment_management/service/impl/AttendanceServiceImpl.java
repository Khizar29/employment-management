package com.paysyslabs.employment_management.service.impl;

import com.paysyslabs.employment_management.dto.AttendanceDTO;
import com.paysyslabs.employment_management.entity.Attendance;
import com.paysyslabs.employment_management.entity.AttendanceStatus;
import com.paysyslabs.employment_management.entity.Employee;
import com.paysyslabs.employment_management.exception.EmployeeNotFoundException;
import com.paysyslabs.employment_management.repository.AttendanceRepository;
import com.paysyslabs.employment_management.repository.EmployeeRepository;
import com.paysyslabs.employment_management.service.AttendanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @Override
    public AttendanceDTO createAttendance(AttendanceDTO attendanceDTO) {
        Employee employee = employeeRepository.findById(attendanceDTO.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(attendanceDTO.getDate());
        attendance.setCheckInTime(attendanceDTO.getCheckInTime());
        attendance.setCheckOutTime(attendanceDTO.getCheckOutTime());
        attendance.setStatus(attendanceDTO.getStatus());

        Attendance savedAttendance = attendanceRepository.save(attendance);
        return mapToDTO(savedAttendance);
    }

    @Override
    public AttendanceDTO getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        return mapToDTO(attendance);
    }

    @Override
    public List<AttendanceDTO> getAttendanceByEmployeeId(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getAllAttendances() {
        return attendanceRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setCheckInTime(attendanceDTO.getCheckInTime());
        attendance.setCheckOutTime(attendanceDTO.getCheckOutTime());
        attendance.setStatus(attendanceDTO.getStatus());

        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return mapToDTO(updatedAttendance);
    }

    @Transactional
    @Override
    public void deleteAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        attendanceRepository.delete(attendance);
    }

    private AttendanceDTO mapToDTO(Attendance attendance) {
        return new AttendanceDTO(
                attendance.getId(),
                attendance.getEmployee().getId(),
                attendance.getDate(),
                attendance.getCheckInTime(),
                attendance.getCheckOutTime(),
                attendance.getStatus()
        );
    }
}
