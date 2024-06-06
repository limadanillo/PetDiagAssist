package br.com.petdiagassist.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamVeterinarianPk implements Serializable {
    @Serial
    private static final long serialVersionUID = 1442337082745098283L;
    private UUID examId;
    private UUID vetId;
}
