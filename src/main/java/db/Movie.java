package db;

public class Movie {
    int movie_id;
    String title;
    int year;
    String headquarter;
    int director_id;

    public Movie(int movie_id, String title, int year, String headquarter, int director_id) {
        this.movie_id = movie_id;
        this.title = title;
        if(year >= 1900 && year <= 2025) {
            this.year = year;
        } else throw new IllegalArgumentException("Year must be between 1900 and 2025");
        this.headquarter = headquarter;
        this.director_id = director_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if(year >= 1900 && year <= 2025) {
            this.year = year;
        } else throw new IllegalArgumentException("Year must be between 1900 and 2025");
    }

    public String getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(String headquarter) {
        this.headquarter = headquarter;
    }

    public int getDirector_id() {
        return director_id;
    }

    public void setDirector_id(int director_id) {
        this.director_id = director_id;
    }
}
