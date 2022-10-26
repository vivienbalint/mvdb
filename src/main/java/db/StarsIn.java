package db;

public record StarsIn(Actor actor, Movie movie) {

    public Actor getActor() {
        return actor;
    }

    public Movie getMovie() {
        return movie;
    }
}
