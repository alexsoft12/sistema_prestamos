package pe.a3ya.msloans.infrastructure.dao;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.a3ya.msloans.infrastructure.entities.PaymentInstallmentEntity;

import java.util.List;
import java.util.Optional;

@NonNullApi
public interface PaymentInstallmentRepository extends JpaRepository<PaymentInstallmentEntity, Long> {
    @Override
    @Query("SELECT pi FROM PaymentInstallmentEntity pi where pi.deletedAt is null")
    List<PaymentInstallmentEntity> findAll();

    @Override
    @Query("SELECT pi FROM PaymentInstallmentEntity pi where pi.id = ?1 and pi.deletedAt is null")
    Optional<PaymentInstallmentEntity> findById(Long id);

    @Override
    default void delete(PaymentInstallmentEntity entity) {
        entity.onDeleted();
        save(entity);
    }
}
