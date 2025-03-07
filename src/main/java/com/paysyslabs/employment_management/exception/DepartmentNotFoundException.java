package com.paysyslabs.employment_management.exception;

public class DepartmentNotFoundException extends RuntimeException{

    public DepartmentNotFoundException(String message)
    {
        super(message);
    }
}
