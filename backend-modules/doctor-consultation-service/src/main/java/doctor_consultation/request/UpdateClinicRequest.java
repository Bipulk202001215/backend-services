package doctor_consultation.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateClinicRequest(
        @NotBlank(message = "Clinic name is required")
        String clinicName,
        String clinicLogo,
        String clinicAddress
) {
}


