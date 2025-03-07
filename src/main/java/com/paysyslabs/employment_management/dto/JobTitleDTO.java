package com.paysyslabs.employment_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Service;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobTitleDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;


    private String description;
}
