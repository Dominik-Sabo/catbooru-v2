package com.sabo.catbooru.repository;

import com.sabo.catbooru.model.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
}
