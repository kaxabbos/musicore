package com.musicore.repo;

import com.musicore.models.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoIncomes extends JpaRepository<Income, Long> {
}
