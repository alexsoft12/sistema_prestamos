package pe.a3ya.mscustomers.infraestructure.dao;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.a3ya.mscustomers.infraestructure.entities.AddressEntity;

import java.util.List;
import java.util.Optional;

@NonNullApi
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    @Override
    @Query("SELECT a FROM AddressEntity a where a.deletedAt is null")
    List<AddressEntity> findAll();

    @Override
    @Query("SELECT a FROM AddressEntity a where a.id = ?1 and a.deletedAt is null")
    Optional<AddressEntity> findById(Long id);

    @Override
    default void delete(AddressEntity entity) {
        save(entity);
    }
}
