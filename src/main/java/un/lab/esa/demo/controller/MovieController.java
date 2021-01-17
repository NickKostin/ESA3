package un.lab.esa.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import un.lab.esa.demo.model.Movie;
import un.lab.esa.demo.repository.MovieRepository;

import javax.websocket.server.PathParam;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.Optional;

@RestController
public class MovieController {

    private MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping(value = "/movies")
    public ModelAndView getXSLMovies() throws JsonProcessingException {
        String data = new XmlMapper().writeValueAsString(movieRepository.findAll());
        ModelAndView modelAndView = new ModelAndView("movie-list");
        Source src = new StreamSource(new StringReader(data));
        modelAndView.addObject("MoviesList", src);
        return modelAndView;
    }

    @GetMapping(value = "/getAllMovies", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE  })
    public Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/getMovieById")
    public Movie getMovieById(@PathParam("id") int id) throws Exception {
        Optional<Movie> requestedMovie = movieRepository.findById(id);
        if (requestedMovie.isPresent())
            return requestedMovie.get();
        else throw new Exception("There is no movie with such id");
    }

    @PostMapping("/createMovie")
    public Movie createMovie(@RequestBody Movie movieToCreate) {
        return movieRepository.save(movieToCreate);
    }

    @PutMapping("/updateMovie")
    public Movie updateMovie(@RequestBody Movie movieToUpdate) {
        Optional<Movie> existingMovie = movieRepository.findById(movieToUpdate.getId());
        if (existingMovie.isPresent()) {
            Movie movieToSave = existingMovie.get();
            movieToSave.setTitle(movieToUpdate.getTitle());
            movieToSave.setPrice(movieToUpdate.getPrice());
            movieToSave.setYear(movieToUpdate.getYear());
            movieToSave.setDirectors(movieToUpdate.getDirectors());
            return movieRepository.save(movieToSave);
        }
        else return movieRepository.save(movieToUpdate);
    }

    @DeleteMapping("/deleteMovieById")
    public void deleteMovieById(@PathParam("id") int id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        }
    }

    @DeleteMapping("/deleteAllMovies")
    public void deleteAllMovies() {
        movieRepository.deleteAll();
    }


}