package pe.a3ya.mscustomers.infraestructure.dao;

import pe.a3ya.mscustomers.infraestructure.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
