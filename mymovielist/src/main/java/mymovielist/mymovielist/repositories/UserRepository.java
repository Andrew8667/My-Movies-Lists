package mymovielist.mymovielist.repositories;

import mymovielist.mymovielist.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the user entity
 * Provides JPA methods
 * @author Andrew Gee
 */
public interface UserRepository extends JpaRepository<User,String> {
}
