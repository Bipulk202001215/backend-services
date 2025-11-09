package doctor_consultation.controller;

import doctor_consultation.request.CreateTokenRequest;
import doctor_consultation.response.TokenResponse;
import doctor_consultation.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tokens")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> createToken(@Valid @RequestBody CreateTokenRequest request) {
        TokenResponse response = tokenService.createToken(request);
        return ResponseEntity
                .created(URI.create("/api/v1/tokens/" + response.tokenId()))
                .body(response);
    }
}

