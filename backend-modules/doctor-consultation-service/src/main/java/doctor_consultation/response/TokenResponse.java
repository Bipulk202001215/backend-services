package doctor_consultation.response;

import doctor_consultation.entity.TokenStatus;
import java.time.Instant;

public record TokenResponse(
        String tokenId,
        String name,
        Integer age,
        String number,
        String clinicId,
        Integer tokenNumber,
        TokenStatus status,
        String transcript,
        String summaryData,
        Instant createdAt,
        Instant updatedAt
) {
}

