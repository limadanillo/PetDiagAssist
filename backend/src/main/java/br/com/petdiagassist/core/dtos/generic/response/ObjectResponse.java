package br.com.petdiagassist.core.dtos.generic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Class Data Transfer Object List generic Response
 * @author Danillo Lima
 * @since 01/03/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjectResponse {
    private Integer statusCode;
    private Object data;
}
