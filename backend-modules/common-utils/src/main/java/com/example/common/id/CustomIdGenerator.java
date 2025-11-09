package com.example.common.id;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.SecureRandom;

@Component
public class CustomIdGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    public String generate(String prefix, String separator, int length) {
        Assert.isTrue(length > 0, "length must be greater than zero");

        StringBuilder builder = new StringBuilder();
        if (prefix != null && !prefix.isBlank()) {
            builder.append(prefix);
            if (separator != null && !separator.isBlank()) {
                builder.append(separator);
            }
        }

        for (int index = 0; index < length; index++) {
            builder.append(RANDOM.nextInt(10));
        }

        return builder.toString();
    }
}

