package com.youtube_shorts_map.repository;

import com.youtube_shorts_map.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByEmail(String mail);
}
