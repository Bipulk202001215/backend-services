package doctor_consultation.services;

import doctor_consultation.entity.Token;
import doctor_consultation.repository.TokenRepository;
import doctor_consultation.request.CreateTokenRequest;
import doctor_consultation.response.TokenResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public TokenResponse createToken(CreateTokenRequest request) {
        // Get current date
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();
        
        // Auto-generate token number for the clinic on the current date
        int nextTokenNumber = getNextTokenNumber(request.clinicId(), year, month, day);
        
        Token token = new Token();
        token.setName(request.name());
        token.setAge(request.age());
        token.setNumber(request.number());
        token.setClinicId(request.clinicId());
        token.setYear(year);
        token.setMonth(month);
        token.setDay(day);
        token.setTokenNumber(nextTokenNumber);
        token.setTranscript(request.transcript());
        token.setSummaryData(request.summaryData());

        Instant now = Instant.now();
        token.setCreatedAt(now);
        token.setUpdatedAt(now);

        Token savedToken = tokenRepository.save(token);
        return mapToResponse(savedToken);
    }
    
    private int getNextTokenNumber(String clinicId, int year, int month, int day) {
        List<Token> tokens = tokenRepository.findTop1ByClinicIdAndYearAndMonthAndDayOrderByTokenNumberDesc(clinicId, year, month, day);
        if (tokens.isEmpty()) {
            return 1;
        }
        // Get the highest token number for this date and increment by 1
        return tokens.get(0).getTokenNumber() + 1;
    }

    private TokenResponse mapToResponse(Token token) {
        return new TokenResponse(
                token.getTokenId(),
                token.getName(),
                token.getAge(),
                token.getNumber(),
                token.getClinicId(),
                token.getTokenNumber(),
                token.getStatus(),
                token.getTranscript(),
                token.getSummaryData(),
                token.getCreatedAt(),
                token.getUpdatedAt()
        );
    }
}

