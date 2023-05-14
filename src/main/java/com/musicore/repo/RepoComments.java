package com.musicore.repo;

import com.musicore.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoComments extends JpaRepository<Comments, Long> {
}
