package db;

public record Genre(String genreName, Movie movie) {

    public String getGenreName() {
        return genreName;
    }

    public Movie getMovie() {
        return movie;
    }
}
