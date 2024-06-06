package br.com.petdiagassist.dataprovider.database.postgresql;

import br.com.petdiagassist.core.entity.ReportEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportRepository extends ReactiveCrudRepository<ReportEntity, UUID> {
}
