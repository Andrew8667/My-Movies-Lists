package mymovielist.mymovielist.repositories;

import mymovielist.mymovielist.entities.Rating;
import mymovielist.mymovielist.keys.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, RatingKey> {
}
