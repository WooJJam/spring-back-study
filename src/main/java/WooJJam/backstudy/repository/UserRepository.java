package WooJJam.backstudy.repository;

import WooJJam.backstudy.week2.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.refreshToken = :refreshToken WHERE u.email = :email")
    void updateRefreshToken(@Param("email") String email, @Param("refreshToken") String refreshToken);
}
