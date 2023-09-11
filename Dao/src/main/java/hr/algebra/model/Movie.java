/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author s_vre
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "title", "link", "description", "picturePath", "publishedDate", "genres", "actors", "directors"})

public final class Movie implements Comparable<Movie> {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @XmlAttribute
    private int id;
    private String title;
    private String link;
    private String description;
    @XmlElement(name = "picturepath")
    private String picturePath;
    @XmlElement(name = "publisheddate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDateTime publishedDate;
    private Set<Genre> genres;
    private Set<Person> actors;
    private Set<Person> directors;

    //za čitanje sa url-a:
    public Movie() {
    }

    public Movie(String title, String link, String description, String picturePath, LocalDateTime publishedDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.picturePath = picturePath;
        this.publishedDate = publishedDate;
    }

    public Movie(int id, String title, String link, String description, String picturePath, LocalDateTime publishedDate) {
        this(title, link, description, picturePath, publishedDate);
        this.id = id;
    }

    //Za Upload:
    public Movie(String title, String link, String description, String picturePath, LocalDateTime publishedDate, Set<Genre> genres, Set<Person> actors, Set<Person> directors) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.picturePath = picturePath;
        this.publishedDate = publishedDate;
        this.genres = genres;
        this.actors = actors;
        this.directors = directors;
    }

    public int getId() {
        return id;
    }

//Getteri i setteri trebaju zbog Update
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Set<Genre> getGenres() {
        return genres == null ? Collections.emptySet() : genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Person> getActors() {
        return actors == null ? Collections.emptySet() : actors; // Zbog NullPointerException ne smije biti null, nego Empty Set 
    }

    public void setActors(Set<Person> actors) {
        this.actors = actors;
    }

    public Set<Person> getDirectors() {
        return directors == null ? Collections.emptySet() : directors;
    }

    public void setDirectors(Set<Person> directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        return id + " " + title + ", " + link + " " + description + ", " + picturePath + ", " + publishedDate;
    }

    @Override
    public int compareTo(Movie o) {
        return title.compareTo(o.title);
    }

//Equals i hashCode trebaju zbog Delete    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        return Objects.equals(this.title, other.title);
    }

}
