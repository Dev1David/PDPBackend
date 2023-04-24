package com.example.PDPMobileGame.repository;

import com.example.PDPMobileGame.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
   UserEntity findByToken (String token);
   Optional<UserEntity> findByFirstName(String name);
   Optional<UserEntity> findByEmail(String email);
}
