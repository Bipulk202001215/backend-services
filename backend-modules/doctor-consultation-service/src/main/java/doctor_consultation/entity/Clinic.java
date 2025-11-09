package doctor_consultation.entity;

import com.example.common.id.GeneratedId;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.Objects;

@Data
@Document(collection = "clinics")
public class Clinic {

    @Id
    @Field(name = "_id")
    @GeneratedId(prefix = "CDID", length = 10)
    private String clinicId;

    @Field(name = "clinic_name")
    private String clinicName;

    @Field(name = "clinic_logo")
    private String clinicLogo;

    @Field(name = "clinic_address")
    private String clinicAddress;

    @Field(name = "user_id")
    private String userId;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.TIMESTAMP)
    private Instant createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.TIMESTAMP)
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clinic clinic)) return false;
        return Objects.equals(clinicId, clinic.clinicId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clinicId);
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicLogo() {
        return clinicLogo;
    }

    public void setClinicLogo(String clinicLogo) {
        this.clinicLogo = clinicLogo;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }
}


