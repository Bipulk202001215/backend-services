package com.example.login.repository;

import com.example.login.entity.AuthToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthTokenRepository extends MongoRepository<AuthToken, String> {

    Optional<AuthToken> findFirstByUserIdOrderByCreatedAtDesc(UUID userId);

    long deleteByUserId(UUID userId);
}


