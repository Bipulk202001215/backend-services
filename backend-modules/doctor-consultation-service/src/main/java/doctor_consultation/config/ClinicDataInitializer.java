package doctor_consultation.config;

import doctor_consultation.entity.Clinic;
import doctor_consultation.repository.ClinicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
@Component
public class ClinicDataInitializer implements CommandLineRunner {

    private static final String DEFAULT_CLINIC_ID = "CDID-6bc2b100-177e-498b-888b-f1934e53acca";
    private static final OffsetDateTime DEFAULT_TIMESTAMP = OffsetDateTime.of(
            2025, 11, 9, 12, 46, 33, 0, ZoneOffset.ofHoursMinutes(5, 30)
    );

    private final ClinicRepository clinicRepository;

    public ClinicDataInitializer(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    public void run(String... args) {
        if (clinicRepository.existsById(DEFAULT_CLINIC_ID)) {
            return;
        }

        Clinic clinic = new Clinic();
        clinic.setClinicId(DEFAULT_CLINIC_ID);
        clinic.setClinicName("Test");
        clinic.setClinicLogo("");
        clinic.setClinicAddress("");
        clinic.setUserId("QpdFEZBJsBN2oeU7Ie0QOpxIp9s1");
        clinic.setCreatedAt(DEFAULT_TIMESTAMP.toInstant());
        clinic.setUpdatedAt(DEFAULT_TIMESTAMP.toInstant());

        clinicRepository.save(clinic);
    }
}


