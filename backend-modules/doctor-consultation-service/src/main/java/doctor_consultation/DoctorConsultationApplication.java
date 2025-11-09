package doctor_consultation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"doctor_consultation", "com.example.login", "com.example.common"})
public class DoctorConsultationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorConsultationApplication.class, args);
	}

}
