/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.assignment02_francesco;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author super
 */
public class SlideshowController implements Initializable {
    
    private static final double SLIDE_SECONDS = 5;   // delay between pictures
    private static final double CAR_SCALE = 3;

    private final List<Car> cars = new ArrayList<>();
    private final List<String> descriptions = new ArrayList<>();
    private Timeline timeline;
    private MediaPlayer player;
    private int index = 0;
    private AudioClip slideSound;


    @FXML
    private Label greetingLabel;
    @FXML
    private Label carInfoLabel;
    @FXML
    private Label counterLabel;
    @FXML
    private Button skipButton;
    @FXML
    private StackPane carPane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        addCar(new Car(7, Color.LIMEGREEN),   "Car #7 - Steel Ball Run");
        addCar(new Car(1, Color.RED),         "Car #1 - Phantom Blood");
        addCar(new Car(3, Color.GOLD),        "Car #3 - Stardust Crusaders");
        addCar(new Car(6,  Color.DODGERBLUE),  "Car #6 - Stone Ocean");
        
        loadSound();
        showSlide(0);

        timeline = new Timeline(new KeyFrame(Duration.seconds(SLIDE_SECONDS), e -> advance()));
        timeline.setCycleCount(cars.size());
        timeline.play();
    }    
    
    @FXML
    private void skip(ActionEvent event) {
        goToRace();
    }
    
    /**
     * Adds a car to the slideshow.
     * @param car The car to add.
     * @param description the description of the car.
     */
    private void addCar(Car car, String description) {
        car.setScaleX(CAR_SCALE);
        car.setScaleY(CAR_SCALE);
        cars.add(car);
        descriptions.add(description);
    }
    
    /**
     * Shows the current car, its number and its description. 
     * @param i the index to show.
     */
    private void showSlide(int i) {
        carPane.getChildren().setAll(cars.get(i));
        carInfoLabel.setText(descriptions.get(i));
        counterLabel.setText((i + 1) + " / " + cars.size());

        playSlideSound();

        FadeTransition fade = new FadeTransition(Duration.millis(500), cars.get(i));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
    
    /**
     * Advances the slideshow.
     */
    private void advance() {
        index++;
        if (index < cars.size()) {
            showSlide(index);
            
        } else {
            goToRace();
        }
    }
    
    /**
     * Transitions the current FXML to the CarRace FXML.
     */
    private void goToRace() {
        timeline.stop();
        if (slideSound != null) {
            slideSound.stop(); 
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CarRace.fxml"));
            Scene scene = carPane.getScene();
            scene.setRoot(root);
            ((Stage) scene.getWindow()).sizeToScene();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
        
    /**
     * Loads the sound that is played when the slide advances.
     */
    private void loadSound() {
        URL soundUrl = getClass().getResource("engine.mp3"); 
        if (soundUrl == null) {
            System.out.println("Sound file not found");
            return;
        }
        slideSound = new AudioClip(soundUrl.toExternalForm());
    }

    /**
     * Plays the sound.
     */
    private void playSlideSound() {
        if (slideSound != null) {
            slideSound.stop(); 
            slideSound.play();
        }
    }
    

}
