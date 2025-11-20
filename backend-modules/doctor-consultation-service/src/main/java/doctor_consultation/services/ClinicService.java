package doctor_consultation.services;

import doctor_consultation.entity.Clinic;
import doctor_consultation.repository.ClinicRepository;
import doctor_consultation.request.CreateClinicRequest;
import doctor_consultation.request.UpdateClinicRequest;
import doctor_consultation.response.ClinicResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public ClinicResponse createClinic(CreateClinicRequest request) {
        Clinic clinic = new Clinic();
        clinic.setClinicName(request.clinicName());
        clinic.setClinicLogo(request.clinicLogo());
        clinic.setClinicAddress(request.clinicAddress());
        clinic.setUserId(request.userId());
        Instant now = Instant.now();
        clinic.setCreatedAt(now);
        clinic.setUpdatedAt(now);

        Clinic savedClinic = clinicRepository.save(clinic);
        return mapToResponse(savedClinic);
    }

    public ClinicResponse updateClinic(String clinicId, UpdateClinicRequest request) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));

        clinic.setClinicName(request.clinicName());
        if (request.clinicLogo() != null) {
            clinic.setClinicLogo(request.clinicLogo());
        }
        if (request.clinicAddress() != null) {
            clinic.setClinicAddress(request.clinicAddress());
        }
        clinic.setUpdatedAt(Instant.now());

        Clinic savedClinic = clinicRepository.save(clinic);
        return mapToResponse(savedClinic);
    }

    public ClinicResponse getClinicByUserId(String userId) {
        // Get the most recent clinic for the user (in case there are multiple)
        Clinic clinic = clinicRepository.findFirstByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found for user"));
        return mapToResponse(clinic);
    }

    private ClinicResponse mapToResponse(Clinic clinic) {
        return new ClinicResponse(
                clinic.getClinicId(),
                clinic.getClinicName(),
                clinic.getClinicLogo(),
                clinic.getClinicAddress(),
                clinic.getUserId(),
                clinic.getCreatedAt(),
                clinic.getUpdatedAt()
        );
    }
}


