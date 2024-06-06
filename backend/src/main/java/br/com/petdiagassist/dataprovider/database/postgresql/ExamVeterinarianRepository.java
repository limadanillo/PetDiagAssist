package br.com.petdiagassist.dataprovider.database.postgresql;

import br.com.petdiagassist.core.entity.ExamVeterinarianEntity;
import br.com.petdiagassist.core.entity.ExamVeterinarianPk;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamVeterinarianRepository extends ReactiveCrudRepository<ExamVeterinarianEntity, ExamVeterinarianPk> {
}
