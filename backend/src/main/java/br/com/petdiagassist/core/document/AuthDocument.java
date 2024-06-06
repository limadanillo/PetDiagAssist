package br.com.petdiagassist.core.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class Black-List Document
 *
 * @author Danillo Lima
 * @since 18/02/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("black_list")
public class AuthDocument implements GenericDocument {

    @Id
    private String id;

    private String token;
}
