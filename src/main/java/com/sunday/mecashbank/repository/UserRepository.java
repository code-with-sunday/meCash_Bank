package com.sunday.mecashbank.repository;

import com.sunday.mecashbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);

    Boolean existsByEmail(String email);
}
