package practice.jpaboard.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.jpaboard.domain.member.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoles(String roles);
}
