/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author s_vre
 */
@XmlRootElement(name = "moviesxml")
@XmlAccessorType(XmlAccessType.FIELD)

public class MoviesXML {
    
    @XmlElementWrapper
    @XmlElement(name = "movie")
    
    private List<Movie> movies;

    public MoviesXML() {
    }

    public MoviesXML(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getmovies() {
        return movies;
    }
}
