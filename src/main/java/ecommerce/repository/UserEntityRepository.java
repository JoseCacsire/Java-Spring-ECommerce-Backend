package ecommerce.repository;

import ecommerce.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);//poniendo fyndBy ya jpa sabe q metodo aplicar
}
