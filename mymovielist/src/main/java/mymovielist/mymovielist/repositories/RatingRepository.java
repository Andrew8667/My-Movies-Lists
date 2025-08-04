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
    /**
     * Finds ratings by the user who made the rating and the movie the rating is for
     * @param user that made the rating
     * @param movie the rating is for
     * @return rating if found, null otherwise
     */
    public Optional<Rating> findByUserAndMovie(User user, Movie movie);

    /**
     * Checks if there exists a rating that was made by a user for a movie
     * @param user who made the rating
     * @param movie rating is for
     * @return true if there is a rating, false otherwise
     */
    public boolean existsByUserAndMovie(User user,Movie movie);
}
