package pe.a3ya.mscustomers.infraestructure.dao;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.Query;
import pe.a3ya.mscustomers.infraestructure.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@NonNullApi
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Override
    @Query("SELECT c FROM CustomerEntity c where c.deletedAt is null")
    List<CustomerEntity> findAll();

    @Override
    @Query("SELECT c FROM CustomerEntity c where c.id = ?1 and c.deletedAt is null")
    Optional<CustomerEntity> findById(Long id);

    @Override
    default void delete(CustomerEntity entity) {
        save(entity);
    }
}
