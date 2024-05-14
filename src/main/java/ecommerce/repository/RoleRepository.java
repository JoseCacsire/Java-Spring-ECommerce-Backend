package ecommerce.repository;

import ecommerce.model.Role;
import ecommerce.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity,Long> {
    RoleEntity findByName(Role role);
}
