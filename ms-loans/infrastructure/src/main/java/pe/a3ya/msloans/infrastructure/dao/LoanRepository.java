package pe.a3ya.msloans.infrastructure.dao;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.a3ya.msloans.infrastructure.entities.LoanEntity;

import java.util.List;
import java.util.Optional;

@NonNullApi
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    @Override
    @Query("SELECT l FROM LoanEntity l where l.deletedAt is null")
    List<LoanEntity> findAll();

    @Override
    @Query("SELECT l FROM LoanEntity l where l.id = ?1 and l.deletedAt is null")
    Optional<LoanEntity> findById(Long id);

    @Override
    default void delete(LoanEntity entity) {
        save(entity);
    }
}
