package jobtes.kadua4.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jobtes.kadua4.model.AdminMDL;
import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<AdminMDL, UUID> {
//    @Query("SELECT a FROM AdminMDL a WHERE a.username = :username")
    Optional<AdminMDL> findByUsername(@Param("username") String username);
}
