package doctor_consultation.repository;

import doctor_consultation.entity.Clinic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClinicRepository extends MongoRepository<Clinic, String> {
    Optional<Clinic> findByUserId(String userId);
}


