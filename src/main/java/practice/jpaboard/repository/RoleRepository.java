package practice.jpaboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.jpaboard.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoles(String roles);
}
