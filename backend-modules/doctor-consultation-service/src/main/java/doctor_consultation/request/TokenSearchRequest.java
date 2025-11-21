package doctor_consultation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TokenSearchRequest(
        @NotBlank(message = "Clinic ID is required")
        String clinicId,
        @NotNull(message = "Year is required")
        Integer year,
        @NotNull(message = "Month is required")
        Integer month,
        @NotNull(message = "Day is required")
        Integer day
) {
}

