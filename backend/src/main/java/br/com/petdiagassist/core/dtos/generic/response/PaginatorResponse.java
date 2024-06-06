package br.com.petdiagassist.core.dtos.generic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Data Transfer Object List Paginator Response
 * @author Danillo Lima
 * @since 01/03/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatorResponse {
    private Integer statusCode;
    private DataResponse data;
}
