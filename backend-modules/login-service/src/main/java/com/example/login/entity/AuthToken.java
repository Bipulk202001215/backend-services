package com.example.login.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "auth_tokens")
public class AuthToken {

    @Id
    private String token;

    @Field(name = "user_id")
    private UUID userId;

    @CreatedDate
    @Field(name = "created_at")
    private Instant createdAt;

    protected AuthToken() {
    }

    public AuthToken(UUID userId) {
        this.token = UUID.randomUUID().toString().replace("-", "");
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public UUID getUserId() {
        return userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthToken authToken)) return false;
        return Objects.equals(token, authToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(token);
    }
}
