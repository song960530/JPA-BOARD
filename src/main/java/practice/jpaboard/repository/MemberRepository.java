package practice.jpaboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.jpaboard.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserId(String userId);
}
