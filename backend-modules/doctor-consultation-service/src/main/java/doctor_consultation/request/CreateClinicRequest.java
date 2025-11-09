package doctor_consultation.request;

import jakarta.validation.constraints.NotBlank;

public record CreateClinicRequest(
        @NotBlank(message = "Clinic name is required")
        String clinicName,
        String clinicLogo,
        String clinicAddress,
        @NotBlank(message = "User ID is required")
        String userId
) {
}


