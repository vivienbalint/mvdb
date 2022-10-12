package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public class ActorDAO {

    public void insertActor(Actor actor) {
        String query = "INSERT INTO szinesz(vezeteknev, keresztnev, szuletesi_ido, nem) VALUES ('" + actor.getLastName() + "', '" + actor.getFirstName() + "', '" + actor.getDateOfBirth() + "', '" + actor.getSex() + "')";
        DbDAO.executeUpdate(query);
    }

    public ObservableList<Actor> listActors() {
        String query = "SELECT * FROM szinesz";

        ObservableList<Actor> result = FXCollections.observableArrayList();
        List<Map<String, Object>> data = DbDAO.executeQuery(query);

        if (data != null) {
            for (Map<String, Object> row : data) {
                Actor actor = new Actor(Integer.parseInt(row.get("szinesz_id").toString()), row.get("keresztnev").toString(), row.get("vezeteknev").toString(), java.sql.Date.valueOf(row.get("szuletesi_ido").toString()), Integer.parseInt(row.get("nem").toString()));
                result.add(actor);
            }
            return result;
        } else return null;
    }

    public void deleteActor(Actor actor) {
        String query = "DELETE FROM szinesz WHERE szinesz_id=" + actor.getActor_id();
        DbDAO.executeUpdate(query);
    }

    public void updateActor(Actor actor) {
        String query = "UPDATE szinesz SET vezeteknev'" + actor.getLastName() + "', keresztnev='" + actor.getFirstName() + "', szuletesi_ido='" + actor.getDateOfBirth() + "', nem= '" + actor.getSex() + "' WHERE szinesz_id=" + actor.getActor_id();
        DbDAO.executeUpdate(query);
    }
}
