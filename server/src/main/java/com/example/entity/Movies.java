package com.example.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "movies")
@XmlAccessorType(XmlAccessType.FIELD)
public class Movies {

    @XmlElement(name = "movie")
    private List<Movie> films = null;

    public List<Movie> getFilms(){
        return films;
    }

    public void setFilms(List<Movie> newMovies) {
        this.films = newMovies;
    }
}
