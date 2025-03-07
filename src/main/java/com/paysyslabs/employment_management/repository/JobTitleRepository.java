package com.paysyslabs.employment_management.repository;

import com.paysyslabs.employment_management.entity.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTitleRepository extends JpaRepository <JobTitle, Long> {
}
