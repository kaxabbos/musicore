package com.musicore.repo;

import com.musicore.models.Devices;
import com.musicore.models.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoDevices extends JpaRepository<Devices, Long> {
    List<Devices> findAllByCategory(Category category);

    List<Devices> findAllByCategoryAndNameContaining(Category category, String name);

    List<Devices> findAllByNameContaining(String name);
}
