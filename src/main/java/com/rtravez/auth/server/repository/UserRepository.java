package com.rtravez.auth.server.repository;

import com.rtravez.auth.server.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = {"person", "roleUsers", "roleUsers.role"})
    Optional<UserEntity> findByUsernameAndStatusTrue(String username);
}
