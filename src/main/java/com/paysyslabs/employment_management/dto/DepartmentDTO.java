package com.paysyslabs.employment_management.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DepartmentDTO {
    private Long id;
    private String name;

    public String getName() {
        return name;
    }

}
