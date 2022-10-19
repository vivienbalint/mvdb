package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public class StarsInDAO {

    public void insertStarsIn(StarsIn starsIn) {
        String query = "INSERT INTO szerepel(film_id_szerepel, szinesz_id_szerepel) VALUES ('" + starsIn.getMovie().getMovie_id() + "', '" + starsIn.getActor().getActor_id() + "')";
        DbDAO.executeUpdate(query);
    }

    public ObservableList<StarsIn> listStarsIn() {
        String query = "SELECT * FROM szerepel";
        ObservableList<StarsIn> result = FXCollections.observableArrayList();
        List<Map<String, Object>> data = DbDAO.executeQuery(query);
        if (data != null) {
            for (Map<String, Object> row : data) {
                MovieDAO movieDAO = new MovieDAO();
                ActorDAO actorDAO = new ActorDAO();
                StarsIn starsIn = new StarsIn(actorDAO.getActorById(Integer.parseInt(row.get("szinesz_id_szerepel").toString())), movieDAO.getMovieById(Integer.parseInt(row.get("film_id_szerepel").toString())));
                result.add(starsIn);

            }
            return result;
        } else return null;
    }

    public void deleteStarsIn(StarsIn starsIn) {
        String query = "DELETE FROM szerepel WHERE film_id_szerepel='" + starsIn.getMovie().getMovie_id() + "' AND szinesz_id_szerepel=" + starsIn.getActor().getActor_id();
        DbDAO.executeUpdate(query);
    }

    public void updateStarsIn(StarsIn starsIn) {
        String query = "UPDATE szerepel SET szinesz_id_szerepel='" + starsIn.getActor().getActor_id() + "' WHERE film_id_szerepel='" + starsIn.getMovie().getMovie_id() + "' AND szinesz_id_szerepel=" + starsIn.getActor().getActor_id();
        DbDAO.executeUpdate(query);
    }
}
