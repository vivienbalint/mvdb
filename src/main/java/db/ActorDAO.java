package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ActorDAO {

    public void insertActor(Actor actor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse(actor.getDateOfBirth(), formatter));
        String query = "INSERT INTO szinesz(vezeteknev, keresztnev, szuletesi_ido, nem) VALUES ('" + actor.getLastName() + "', '" + actor.getFirstName() + "', '" + date + "', '" + actor.getSex() + "')";
        DbDAO.executeUpdate(query);
    }

    public ObservableList<Actor> listActors() {
        String query = "SELECT * FROM szinesz";

        ObservableList<Actor> result = FXCollections.observableArrayList();
        List<Map<String, Object>> data = DbDAO.executeQuery(query);

        if (data != null) {
            for (Map<String, Object> row : data) {
                Actor actor = new Actor(Integer.parseInt(row.get("szinesz_id").toString()), row.get("keresztnev").toString(), row.get("vezeteknev").toString(), row.get("szuletesi_ido").toString(), Integer.parseInt(row.get("nem").toString()));
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse(actor.getDateOfBirth(), formatter));
        String query = "UPDATE szinesz SET vezeteknev='" + actor.getLastName() + "', keresztnev='" + actor.getFirstName() + "', szuletesi_ido='" + date + "', nem= '" + actor.getSex() + "' WHERE szinesz_id=" + actor.getActor_id();
        DbDAO.executeUpdate(query);
    }
}
