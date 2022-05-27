package com.oxyzo.chatlog.repository;

import com.oxyzo.chatlog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for User domain
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
