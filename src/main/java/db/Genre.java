package db;

public class Genre {
    String genreName;
    Movie movie;

    public Genre(String genreName, Movie movie) {
        this.genreName = genreName;
        this.movie = movie;
    }

    public String getGenreName() {
        return genreName;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
