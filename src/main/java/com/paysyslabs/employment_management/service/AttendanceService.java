package com.paysyslabs.employment_management.service;

import com.paysyslabs.employment_management.dto.AttendanceDTO;

import java.util.List;

public interface AttendanceService {
    AttendanceDTO createAttendance(AttendanceDTO attendanceDTO);
    AttendanceDTO getAttendanceById(Long id);
    List<AttendanceDTO> getAttendanceByEmployeeId(Long employeeId);
    List<AttendanceDTO> getAllAttendances();
    AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO);
    void deleteAttendance(Long id);
}
