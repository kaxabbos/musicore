package com.musicore.repo;

import com.musicore.models.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoCarts extends JpaRepository<Carts, Long> {
}
