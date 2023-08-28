package com.zipsoft.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zipsoft.model.entity.User;

@Repository
public interface AuthRepo extends JpaRepository<User, Long> {

}
