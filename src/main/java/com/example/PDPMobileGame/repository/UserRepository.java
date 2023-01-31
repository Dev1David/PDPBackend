package com.example.PDPMobileGame.repository;

import com.example.PDPMobileGame.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query(value = "select * from users where name = ?1", nativeQuery = true)
    UserEntity findByUserName(String name);
}
