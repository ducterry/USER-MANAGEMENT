package com.ndangducbn.ducterrybase.Repository;

import com.ndangducbn.ducterrybase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Integer userId);
    User findUserByEmail(String userEmail);

    Boolean existsByEmail(String userEmail);
    Boolean existsByPhone(String phone);
}
