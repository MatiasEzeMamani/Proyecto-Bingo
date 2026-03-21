package com.matias.bingo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matias.bingo.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
