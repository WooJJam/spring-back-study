package WooJJam.backstudy.week2.Repository;

import WooJJam.backstudy.week2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
