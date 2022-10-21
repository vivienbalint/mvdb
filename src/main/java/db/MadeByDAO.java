package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public class MadeByDAO {

    public void insertMadeBy(MadeBy madeBy) {
        String query = "INSERT INTO gyartotta(studio_id_gyartotta, film_id_gyartotta) VALUES ('" + madeBy.getStudio().getStudio_id() + "', '" + madeBy.getMovie().getMovie_id() + "')";
        DbDAO.executeUpdate(query);
    }

    public ObservableList<MadeBy> listMadeBy() {
        String query = "SELECT * FROM gyartotta";
        ObservableList<MadeBy> result = FXCollections.observableArrayList();
        List<Map<String, Object>> data = DbDAO.executeQuery(query);
        if (data != null) {
            for (Map<String, Object> row : data) {
                StudioDAO studioDAO = new StudioDAO();
                MovieDAO movieDAO = new MovieDAO();
                MadeBy madeBy = new MadeBy(studioDAO.getStudioById(Integer.parseInt(row.get("studio_id_gyartotta").toString())), movieDAO.getMovieById(Integer.parseInt(row.get("film_id_gyartotta").toString())));
                result.add(madeBy);

            }
            return result;
        } else return null;
    }

    public void deleteMadeBy(MadeBy madeBy) {
        String query = "DELETE FROM gyartotta WHERE studio_id_gyartotta='" + madeBy.getStudio().getStudio_id() + "' AND film_id_gyartotta=" + madeBy.getMovie().getMovie_id();
        DbDAO.executeUpdate(query);
    }
}
