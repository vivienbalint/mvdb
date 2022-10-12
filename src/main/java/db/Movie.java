package db;

import java.util.List;

public class Movie {
    int movie_id;
    String title;
    int year;
    int length;
    Director director;
    List<Studio> madeBy;
    List<Actor> stars;

    public Movie(int movie_id, String title, int year, int length, Director director, List<Studio> madeBy, List<Actor> stars) {
        this.movie_id = movie_id;
        this.title = title;
        if(year >= 1900 && year <= 2025) {
            this.year = year;
        } else throw new IllegalArgumentException("Year must be between 1900 and 2025");
        if(length >= 5 && length <= 600) {
            this.length = length;
        } else throw new IllegalArgumentException("Length must be between 5 and 600 minutes");
        this.director = director;
        this.madeBy = madeBy;
        this.stars = stars;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getLength() {
        return length;
    }

    public Director getDirector() {
        return director;
    }

    public List<Studio> getMadeBy() {
        return madeBy;
    }

    public List<Actor> getStars() {
        return stars;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        if(year >= 1900 && year <= 2025) {
            this.year = year;
        } else throw new IllegalArgumentException("Year must be between 1900 and 2025");
    }

    public void setLength(int length) {
        if(length >= 5 && length <= 600) {
            this.length = length;
        } else throw new IllegalArgumentException("Length must be between 5 and 600 minutes");

    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void setMadeBy(List<Studio> madeBy) {
        this.madeBy = madeBy;
    }

    public void setStars(List<Actor> stars) {
        this.stars = stars;
    }
}
