package pe.a3ya.msauth.infrastructure.dao;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.a3ya.msauth.infrastructure.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@NonNullApi
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Override
    @Query("SELECT u FROM UserEntity u where u.deletedAt is null")
    List<UserEntity> findAll();

    @Override
    @Query("SELECT u FROM UserEntity u where u.id = ?1 and u.deletedAt is null")
    Optional<UserEntity> findById(Long id);

    @Override
    default void delete(UserEntity entity) {
        save(entity);
    }

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}
