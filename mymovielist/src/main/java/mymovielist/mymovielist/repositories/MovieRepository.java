package mymovielist.mymovielist.repositories;

import mymovielist.mymovielist.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    public Optional<Movie> findByTitle(String title);
}
