package com.terra.assignment.domain.usage.repository;

import com.terra.assignment.domain.usage.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsageRepository extends JpaRepository<Usage, Long> {
}
