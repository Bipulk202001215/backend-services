package doctor_consultation.response;

import java.time.Instant;

public record TokenResponse(
        String tokenId,
        String name,
        Integer age,
        String number,
        String companyId,
        Integer tokenNumber,
        String status,
        String transcript,
        String summaryData,
        Instant createdAt,
        Instant updatedAt
) {
}

