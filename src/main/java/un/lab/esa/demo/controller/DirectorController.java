package un.lab.esa.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import un.lab.esa.demo.model.Director;
import un.lab.esa.demo.repository.DirectorRepository;

import javax.websocket.server.PathParam;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.Optional;

@RestController
public class DirectorController {

    private DirectorRepository directorRepository;

    @Autowired
    public  DirectorController(DirectorRepository directorRepository){
        this.directorRepository = directorRepository;
    }

    @GetMapping(value = "/directors")
    public ModelAndView getXSLDirectors() throws JsonProcessingException {
        String data = new XmlMapper().writeValueAsString(directorRepository.findAll());
        ModelAndView modelAndView = new ModelAndView("director-list");
        Source src = new StreamSource(new StringReader(data));
        modelAndView.addObject("ArrayList", src);
        return modelAndView;
    }

    @GetMapping(value = "/getAllDirectors", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE  })
    public Iterable<Director> getAllDirectors(){
        return directorRepository.findAll();
    }

    @GetMapping(value = "/getDirectorById", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE  })
    public Director getDirectorById(@PathParam("id") int id) throws Exception {
        Optional<Director> requestedDirector = directorRepository.findById(id);
        if (requestedDirector.isPresent()){
            return requestedDirector.get();
        }
        else throw new Exception("There is no director with such id");
    }

    @PostMapping("/createDirector")
    public Director createDirector(@RequestBody Director directorToCreate){
        return directorRepository.save(directorToCreate);
    }

    @PutMapping("/updateDirector")
    public Director updateDirector(@RequestBody Director directorToUpdate){
        Optional<Director> existingDirector = directorRepository.findById(directorToUpdate.getId());
        if (existingDirector.isPresent()){
            Director directorToSave = existingDirector.get();
            directorToSave.setName(directorToUpdate.getName());
            directorToSave.setSurname(directorToUpdate.getSurname());
            return directorRepository.save(directorToSave);
        }
        else return directorRepository.save(directorToUpdate);
    }

    @DeleteMapping("/deleteDirector")
    public void deleteDirector(@PathParam("id") int id){
        if (directorRepository.existsById(id)){
            directorRepository.deleteById(id);
        }
    }

    @DeleteMapping("/deleteAllDirectors")
    public void deleteAllDirectors(){
        directorRepository.deleteAll();
    }
}
