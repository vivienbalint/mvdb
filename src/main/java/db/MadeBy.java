package db;

public class MadeBy {

    Studio studio;
    Movie movie;

    public MadeBy(Studio studio, Movie movie) {
        this.studio = studio;
        this.movie = movie;
    }

    public Studio getStudio() {
        return studio;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
