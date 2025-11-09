package doctor_consultation.response;

import java.time.Instant;
public record ClinicResponse(
        String clinicId,
        String clinicName,
        String clinicLogo,
        String clinicAddress,
        String userId,
        Instant createdAt,
        Instant updatedAt
) {
}


