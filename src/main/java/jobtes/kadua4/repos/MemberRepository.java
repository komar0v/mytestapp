package jobtes.kadua4.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import jobtes.kadua4.model.MemberMDL;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<MemberMDL, UUID> {
    Optional<MemberMDL> findByEmail(@Param("email") String email);
    List<MemberMDL> findByNameContainingIgnoreCase(String name);
}
