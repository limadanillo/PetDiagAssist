package br.com.petdiagassist.core.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("images")
public class ImageFile {
    @Id
    private UUID id;
    private String title; // Optional: title or description of the image
    private LocalDateTime createdAt;
    private String fileId; // This will be the GridFS file ID
}
