package com.mixedcs.academy.repository;

import com.mixedcs.academy.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {

    Roles getByRoleName(String roleName);
}
