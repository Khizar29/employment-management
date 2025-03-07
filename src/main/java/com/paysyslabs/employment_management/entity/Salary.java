package com.paysyslabs.employment_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "basic_salary", nullable = false)
    private BigDecimal basicSalary;

    @Column(name = "bonus", nullable = false)
    private BigDecimal bonus = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "employee_id", unique = true, nullable = false, foreignKey = @ForeignKey(name = "fk_salary_employee"))
    private Employee employee;
}
