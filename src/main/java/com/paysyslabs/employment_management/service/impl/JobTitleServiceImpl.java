package com.paysyslabs.employment_management.service.impl;

import com.paysyslabs.employment_management.dto.JobTitleDTO;
import com.paysyslabs.employment_management.entity.JobTitle;
import com.paysyslabs.employment_management.repository.JobTitleRepository;
import com.paysyslabs.employment_management.service.JobTitleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobTitleServiceImpl implements JobTitleService {

    private final JobTitleRepository jobTitleRepository;

    public JobTitleServiceImpl(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }


    @Transactional
    @Override
    public JobTitleDTO createJobTitle(@Valid JobTitleDTO jobTitleDTO) {
        JobTitle jobTitle = JobTitle.builder()
                .title(jobTitleDTO.getTitle())
                .description(jobTitleDTO.getDescription())
                .build();
        return mapToDTO(jobTitleRepository.save(jobTitle));
    }

    @Override
    public List<JobTitleDTO> getAllJobTitles() {
        return jobTitleRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JobTitleDTO getJobTitleById(Long id) {
        return jobTitleRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Job Title not found"));
    }
    @Transactional
    @Override
    public void deleteJobTitle(Long id) {
        jobTitleRepository.deleteById(id);
    }

    private JobTitleDTO mapToDTO(JobTitle jobTitle) {
        return JobTitleDTO.builder()
                .id(jobTitle.getId())
                .title(jobTitle.getTitle())
                .description(jobTitle.getDescription())
                .build();
    }
}
