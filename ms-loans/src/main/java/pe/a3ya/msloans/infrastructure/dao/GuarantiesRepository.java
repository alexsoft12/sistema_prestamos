package pe.a3ya.msloans.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.a3ya.msloans.infrastructure.entities.GuarantiesEntity;


import java.util.List;
import java.util.Optional;

public interface GuarantiesRepository extends JpaRepository<GuarantiesEntity, Long> {

    @Override
    @Query("SELECT a FROM GuarantiesEntity a where a.deletedAt is null")
    List<GuarantiesEntity> findAll();

    @Override
    @Query("SELECT a FROM GuarantiesEntity a where a.id = ?1 and a.deletedAt is null")
    Optional<GuarantiesEntity> findById(Long id);

    @Override
    default void delete(GuarantiesEntity entity) {
        save(entity);
    }
}
