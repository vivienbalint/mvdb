package db;

public record MadeBy(Studio studio, Movie movie) {

    public Studio getStudio() {
        return studio;
    }

    public Movie getMovie() {
        return movie;
    }
}
