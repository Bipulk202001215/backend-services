package doctor_consultation.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTokenRequest(
        @NotBlank(message = "Name is required")
        String name,
        @Min(value = 0, message = "Age must be greater than or equal to 0")
        Integer age,
        @NotBlank(message = "Number is required")
        String number,
        @NotBlank(message = "Clinic ID is required")
        String clinicId,
        String transcript,
        String summaryData
) {
}

