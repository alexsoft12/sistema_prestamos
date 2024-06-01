package pe.a3ya.mspayments.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.a3ya.mspayments.infrastructure.entities.PayEntity;

import java.util.List;
import java.util.Optional;

public interface PayRepository extends JpaRepository<PayEntity, Long> {

    @Override
    @Query("SELECT l FROM PayEntity l where l.deletedAt is null")
    List<PayEntity> findAll();

    @Override
    @Query("SELECT l FROM PayEntity l where l.id = ?1 and l.deletedAt is null")
    Optional<PayEntity> findById(Long id);

    @Override
    default void delete(PayEntity entity) {
        save(entity);
    }
}
