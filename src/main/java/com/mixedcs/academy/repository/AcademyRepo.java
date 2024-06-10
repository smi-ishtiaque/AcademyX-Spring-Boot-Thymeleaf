package com.mixedcs.academy.repository;

import com.mixedcs.academy.model.Academy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademyRepo extends JpaRepository<Academy, Integer> {
}
