package com.example.login.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "user_accounts")
public class UserAccount {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(name = "email")
    private String email;

    @Field(name = "password_hash")
    private String passwordHash;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.TIMESTAMP)
    private Instant createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.TIMESTAMP)
    private Instant updatedAt;

    public UserAccount() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


