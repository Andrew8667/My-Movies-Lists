package mymovielist.mymovielist.repositories;

import mymovielist.mymovielist.entities.Movie;
import mymovielist.mymovielist.entities.Rating;
import mymovielist.mymovielist.entities.User;
import mymovielist.mymovielist.keys.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for the rating entity
 * Provides JPA methods
 * @author Andrew Gee
 */
public interface RatingRepository extends JpaRepository<Rating, RatingKey> {
    public Optional<Rating> findByUserAndMovie(User user, Movie movie);
    public boolean existsByUserAndMovie(User user,Movie movie);
}
