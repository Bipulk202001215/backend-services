package doctor_consultation.repository;

import doctor_consultation.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TokenRepository extends MongoRepository<Token, String> {
    
    @Query(value = "{ 'clinic_id': ?0, 'year': ?1, 'month': ?2, 'day': ?3 }", sort = "{ 'token_number': -1 }")
    List<Token> findTop1ByClinicIdAndYearAndMonthAndDayOrderByTokenNumberDesc(String clinicId, Integer year, Integer month, Integer day);
}

