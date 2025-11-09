package doctor_consultation.repository;

import doctor_consultation.entity.Clinic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClinicRepository extends MongoRepository<Clinic, String> {
}


