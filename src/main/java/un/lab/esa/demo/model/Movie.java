package un.lab.esa.demo.model;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(schema = "public", name="movie")
@JacksonXmlRootElement(localName = "Movie")
public class Movie {
    @Id
    @Column(name = "id")
    @JacksonXmlProperty(isAttribute = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @JacksonXmlProperty
    private String title;

    @Column(name = "price")
    @JacksonXmlProperty
    private double price;

    @Column(name="year")
    @JacksonXmlProperty
    private  int year;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "movie_director",
            joinColumns = {@JoinColumn(name = "id_movie")},
            inverseJoinColumns = {@JoinColumn(name = "id_director")})
    @JacksonXmlProperty
    Collection<Director> directors;

    public Movie() {
    }

    public Movie(int id, String title, double price, int year){
        this.id = id;
        this.title = title;
        this.price = price;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }

    public Collection<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Collection<Director> directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", year=" + year +
                ", directors=" + directors +
                '}';
    }
}
