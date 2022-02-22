package com.prototype.restaurantsreview.repository;

import com.prototype.restaurantsreview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserEmail(String email);
    Optional<User> findByUsername(String username);
    void deleteUserByUsername(String name);
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.userEmail = ?1")
    int enableUser(String email);
}
