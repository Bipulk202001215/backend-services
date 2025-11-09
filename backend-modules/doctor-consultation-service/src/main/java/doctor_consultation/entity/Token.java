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
@Document(collection = "tokens")
public class Token {

    @Id
    @Field(name = "_id")
    @GeneratedId(prefix = "TKID", length = 10)
    private String tokenId;

    @Field(name = "name")
    private String name;

    @Field(name = "age")
    private Integer age;

    @Field(name = "number")
    private String number;

    @Field(name = "company_id")
    private String companyId;

    @Field(name = "status")
    private String status;

    @Field(name = "token_number")
    private Integer tokenNumber;

    @Field(name = "transcript")
    private String transcript;

    @Field(name = "summary_data")
    private String summaryData;

    @CreatedDate
    @Field(name = "created_at", targetType = FieldType.TIMESTAMP)
    private Instant createdAt;

    @LastModifiedDate
    @Field(name = "updated_at", targetType = FieldType.TIMESTAMP)
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token token)) return false;
        return Objects.equals(tokenId, token.tokenId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tokenId);
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(Integer tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getSummaryData() {
        return summaryData;
    }

    public void setSummaryData(String summaryData) {
        this.summaryData = summaryData;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}

