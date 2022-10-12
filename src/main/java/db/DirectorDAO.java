package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public class DirectorDAO {

    public void insertDirector(Director director) {
        String query = "INSERT INTO rendezo(vezeteknev, keresztnev, nem) VALUES ('" + director.getLastName() + "', '" + director.getFirstName() + "', '" + director.isSex() +"')";
        DbDAO.executeUpdate(query);
    }

    public ObservableList<Director> listDirectors(){
        String query = "SELECT * FROM rendezo";

        ObservableList<Director> result = FXCollections.observableArrayList();
        List<Map<String, Object>> data = DbDAO.executeQuery(query);

        if(data != null) {
            for (Map<String, Object> row : data) {
                Director director = new Director(Integer.parseInt(row.get("rendezo_id").toString()), row.get("vezeteknev").toString(), row.get("keresztnev").toString(), Boolean.parseBoolean(row.get("nem").toString()));
                result.add(director);
            }
            return result;
        } else return null;
    }

    public Director getDirectorById(int director_id) {
        String query = "SELECT * FROM rendezo WHERE rendezo_id=" + director_id;
        List<Map<String, Object>> data = DbDAO.executeQuery(query);
        Director director = null;
        if(data != null) {
            for (Map<String, Object> row : data) {
                director = new Director(Integer.parseInt(row.get("rendezo_id").toString()), row.get("vezeteknev").toString(), row.get("keresztnev").toString(), Boolean.parseBoolean(row.get("nem").toString()));
            }
            return director;
        } else return null;
    }

    public void deleteDirector(Director director) {
        String query = "DELETE FROM rendezo WHERE rendezo_id=" + director.getDirector_id();
        DbDAO.executeUpdate(query);
    }

    public void updateDirector(Director director) {
        String query = "UPDATE rendezo SET vezeteknev='" + director.getLastName() + "', keresztnev='" + director.getFirstName() + "', nem= '" + director.isSex() + "' WHERE rendezo_id=" + director.getDirector_id();
        DbDAO.executeUpdate(query);
    }
}
