package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class NavController {
    @FXML
    public BorderPane mainBorderPane;

    @FXML
    public Button searchButton;

    public void initialize() {
        ImageView searchIcon = new ImageView(String.valueOf(getClass().getResource("searchIcon.png")));
        searchIcon.setFitHeight(15);
        searchIcon.setFitWidth(15);
        searchButton.setGraphic(searchIcon);
    }

    @FXML
    private void handleHomeView(ActionEvent e) {
        loadFXML(getClass().getResource("home-view.fxml"));
    }

    @FXML
    private void handleMovieView(ActionEvent e) {
        loadFXML(getClass().getResource("movie-view.fxml"));
    }

    @FXML
    private void handleDirectorView(ActionEvent e) {
        loadFXML(getClass().getResource("director-view.fxml"));
    }

    @FXML
    private void handleStudioView(ActionEvent e) {
        loadFXML(getClass().getResource("studio-view.fxml"));
    }

    @FXML
    private void handleActorView(ActionEvent e) {
        loadFXML(getClass().getResource("actor-view.fxml"));
    }

    @FXML
    private void handleAwardView(ActionEvent e) {
        loadFXML(getClass().getResource("award-view.fxml"));
    }

    @FXML
    private void handleUserView(ActionEvent e) {
        loadFXML(getClass().getResource("user-view.fxml"));
    }

    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            mainBorderPane.setCenter(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}