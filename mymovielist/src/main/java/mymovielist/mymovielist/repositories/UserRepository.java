package mymovielist.mymovielist.repositories;

import mymovielist.mymovielist.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
