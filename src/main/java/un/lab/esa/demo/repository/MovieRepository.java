package un.lab.esa.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import un.lab.esa.demo.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {
}
