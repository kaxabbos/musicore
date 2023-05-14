package com.musicore.repo;

import com.musicore.models.Stores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoStores extends JpaRepository<Stores, Long> {
}
