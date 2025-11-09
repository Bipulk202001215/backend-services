package doctor_consultation.services;

import doctor_consultation.entity.Token;
import doctor_consultation.repository.TokenRepository;
import doctor_consultation.request.CreateTokenRequest;
import doctor_consultation.response.TokenResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public TokenResponse createToken(CreateTokenRequest request) {
        Token token = new Token();
        token.setName(request.name());
        token.setAge(request.age());
        token.setNumber(request.number());
        token.setCompanyId(request.companyId());
        token.setTokenNumber(request.tokenNumber());
        token.setStatus(request.status());
        token.setTranscript(request.transcript());
        token.setSummaryData(request.summaryData());

        Instant now = Instant.now();
        token.setCreatedAt(now);
        token.setUpdatedAt(now);

        Token savedToken = tokenRepository.save(token);
        return mapToResponse(savedToken);
    }

    private TokenResponse mapToResponse(Token token) {
        return new TokenResponse(
                token.getTokenId(),
                token.getName(),
                token.getAge(),
                token.getNumber(),
                token.getCompanyId(),
                token.getTokenNumber(),
                token.getStatus(),
                token.getTranscript(),
                token.getSummaryData(),
                token.getCreatedAt(),
                token.getUpdatedAt()
        );
    }
}

