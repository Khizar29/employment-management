package com.paysyslabs.employment_management.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="jobtitle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JobTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
}
