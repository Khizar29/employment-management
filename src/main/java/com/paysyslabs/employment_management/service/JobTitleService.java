package com.paysyslabs.employment_management.service;


import com.paysyslabs.employment_management.dto.JobTitleDTO;

import java.util.List;

public interface JobTitleService {

    JobTitleDTO createJobTitle(JobTitleDTO jobTitleDTODTO);
    List<JobTitleDTO> getAllJobTitles();
    JobTitleDTO getJobTitleById(Long id);
    void deleteJobTitle(Long id);
}
