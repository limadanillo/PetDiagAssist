package br.com.petdiagassist.core.entity;

import br.com.petdiagassist.core.dtos.bpswf.request.VeterinaryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("veterinarians")
public class VeterinarianEntity implements GenericEntity {
    @Id
    private UUID id;
    private UUID userId;  // Assuming foreign key relationships are handled manually
    private String councilNumber;
    private String state;
    private String councilType;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public VeterinarianEntity(VeterinaryRequest veterinaryRequest, UUID userId) {
        this.userId = userId;
        this.councilNumber = veterinaryRequest.getCouncilNumber();
        this.state = veterinaryRequest.getCouncilState();
        this.councilType = veterinaryRequest.getTypeCouncil();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}

