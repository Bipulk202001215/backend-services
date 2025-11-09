package doctor_consultation.controller;

import doctor_consultation.request.CreateClinicRequest;
import doctor_consultation.request.UpdateClinicRequest;
import doctor_consultation.response.ClinicResponse;
import doctor_consultation.services.ClinicService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
@RestController
@RequestMapping("/api/v1/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PostMapping
    public ResponseEntity<ClinicResponse> createClinic(@Valid @RequestBody CreateClinicRequest request) {
        ClinicResponse response = clinicService.createClinic(request);
        return ResponseEntity
                .created(URI.create("/api/v1/clinics/" + response.clinicId()))
                .body(response);
    }

    @PutMapping("/{clinicId}")
    public ResponseEntity<ClinicResponse> updateClinic(@PathVariable String clinicId,
                                                       @Valid @RequestBody UpdateClinicRequest request) {
        ClinicResponse response = clinicService.updateClinic(clinicId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<ClinicResponse> updateClinic(@PathVariable String userId) {
        ClinicResponse response = clinicService.getClinicByUserId(userId);
        return ResponseEntity.ok(response);
    }
}


