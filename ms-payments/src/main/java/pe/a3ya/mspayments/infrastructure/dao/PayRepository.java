package pe.a3ya.mspayments.infrastructure.dao;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.a3ya.mspayments.infrastructure.entities.PayEntity;

import java.util.List;
import java.util.Optional;

@NonNullApi
public interface PayRepository extends JpaRepository<PayEntity, Long> {

    @Override
    @Query("SELECT p FROM PayEntity p where p.deletedAt is null")
    List<PayEntity> findAll();

    @Override
    @Query("SELECT p FROM PayEntity l where p.id = ?1 and p.deletedAt is null")
    Optional<PayEntity> findById(Long id);

    @Override
    default void delete(PayEntity entity) {
        save(entity);
    }
}
