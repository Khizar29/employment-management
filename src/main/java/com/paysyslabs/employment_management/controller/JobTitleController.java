package com.paysyslabs.employment_management.controller;

import com.paysyslabs.employment_management.constants.JobTitleEndpoints;
import com.paysyslabs.employment_management.dto.JobTitleDTO;
import com.paysyslabs.employment_management.entity.JobTitle;
import com.paysyslabs.employment_management.service.JobTitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "JobTitle", description = "APIs for managing jobtitles")
@RestController
@RequestMapping(JobTitleEndpoints.BASE)
public class JobTitleController {

    private static  final Logger logger = LoggerFactory.getLogger(JobTitle.class);
    private final JobTitleService jobTitleService;

    public JobTitleController(JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    @PostMapping(JobTitleEndpoints.CREATE)
    @Operation(summary = "Create a new JobTitle", description = "Saves a new JobTitle in the system")
    public ResponseEntity<JobTitleDTO> createJobTitle(@Valid @RequestBody JobTitleDTO jobTitleDTO)
    {
        return ResponseEntity.ok(jobTitleService.createJobTitle(jobTitleDTO));
    }

    @GetMapping(JobTitleEndpoints.GET_ALL)
    @Operation(summary = "Get all job titles", description = "Fetches all job titles from the system")
    public ResponseEntity<List<JobTitleDTO>> getAllJobTitles() {
        return ResponseEntity.ok(jobTitleService.getAllJobTitles());
    }

    @GetMapping(JobTitleEndpoints.GET_BY_ID)
    @Operation(summary = "Get job title by ID", description = "Fetches a job title by its ID")
    public ResponseEntity<JobTitleDTO> getJobTitleById(@PathVariable Long id) {
        return ResponseEntity.ok(jobTitleService.getJobTitleById(id));
    }

    @DeleteMapping(JobTitleEndpoints.DELETE)
    @Operation(summary = "Delete job title", description = "Deletes a job title by its ID")
    public ResponseEntity<Void> deleteJobTitle(@PathVariable Long id) {
        jobTitleService.deleteJobTitle(id);
        return ResponseEntity.noContent().build();
    }
}
