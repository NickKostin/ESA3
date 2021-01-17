package un.lab.esa.demo.repository;

import org.springframework.data.repository.CrudRepository;
import un.lab.esa.demo.model.Director;

public interface DirectorRepository extends CrudRepository<Director, Integer> {
}
