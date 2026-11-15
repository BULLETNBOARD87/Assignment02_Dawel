/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.assignment02_francesco;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author 2548213
 */
public class CarRaceController implements Initializable {
    private static final double FINISH_X = 540;
    private static final int TOTAL_CARS = 4;

    private boolean isStopped = false;
    private CarRaceModel model;
    private final List<TranslateTransition> activeAnimations = new ArrayList<>();
    
    private Car[] cars;


    @FXML
    private Rectangle car02;
    @FXML
    private Rectangle car01;
    @FXML
    private Rectangle car03;
    @FXML
    private Rectangle car04;
    @FXML
    private Button startButton;
    @FXML
    private TextArea resultsArea;
    @FXML
    private Button stopButton;
    @FXML
    private Button resetSimulation;
    @FXML
    private Label speedDisplay;
    @FXML
    private Slider simulationSlider;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        speedDisplay.textProperty().bind(simulationSlider.valueProperty().asString("%.2f" + "x"));
        model = new CarRaceModel(resultsArea);

        cars = new Car[]{
            replaceWithCar(car01, 1),
            replaceWithCar(car02, 2),
            replaceWithCar(car03, 3),
            replaceWithCar(car04, 4)
        };
    }
    
    /**
     * Replaces a placeholder rectangle with a car.
     * @param placeholder the rectangle to replace.
     * @param number the number on the car.
     * @return a car.
     */
    private Car replaceWithCar(Rectangle placeholder, int number) {
        Paint fill = placeholder.getFill();
        Color color = (fill instanceof Color) ? (Color) fill : Color.GRAY;

        Car car = new Car(number, color);
        car.setLayoutX(placeholder.getLayoutX());
        car.setLayoutY(placeholder.getLayoutY());

        Pane parent = (Pane) placeholder.getParent();
        parent.getChildren().remove(placeholder);
        parent.getChildren().add(car);
        return car;
    }

    @FXML
    private void startSimulation(ActionEvent event) {
        if (model.allFinished(TOTAL_CARS) || (!activeAnimations.isEmpty() && !isStopped)) {
            return;
        }

        isStopped = false;
        activeAnimations.clear();

        Random random = new Random();
        int min = 1;
        int max = 10;

        for (Rectangle car : new Rectangle[]{car01, car02, car03, car04}) {
            if (!model.isFinished(car)) {  
                startRaceAnimation(car, random, min, max);
            }
        }
    }

    @FXML
    private void stopSImulation(ActionEvent event) {
        isStopped = true;
        for (TranslateTransition t : activeAnimations) {
            t.stop();
        }
        activeAnimations.clear();
    }

    @FXML
    private void resetSimulation(ActionEvent event) {
        isStopped = true;
        for (TranslateTransition t : activeAnimations) {
            t.stop();
        }
        activeAnimations.clear();
        model.reset();
        resetRacePositions(car01, car02, car03, car04);
    }

    /**
     * Starts moving a car.
     * @param car the car to start moving.
     * @param random The random number generator.
     * @param min minimum pixels moved.
     * @param max maximum pixels moved.
     */
    public void startRaceAnimation(Rectangle car, Random random, int min, int max) {
        int randomRange = random.nextInt((max - min) + 1) + min;

        TranslateTransition transition = new TranslateTransition(stepDuration(), car);
        transition.setByX(randomRange);
        transition.setCycleCount(1);

        activeAnimations.add(transition);

        transition.setOnFinished(e -> {
            if (isStopped) {
                return;
            }

            double currentX = car.getLayoutX() + car.getTranslateX();

            if (currentX >= FINISH_X) {
                boolean isWinner = model.recordFinish(car);
                if (isWinner) {
                    model.announceWinner();
                }
                if (model.allFinished(TOTAL_CARS)) {
                    model.showFinalResults();
                    activeAnimations.clear();
                }
            } else {
                int nextStep = random.nextInt((max - min) + 1) + min;
                transition.setByX(nextStep);
                transition.setDuration(stepDuration());
                transition.play();
            }
        });

        transition.play();
    }

    /**
     * Sets each car back to the start. 
     * @param cars the collection of cars to reset the position of. 
     */
    public void resetRacePositions(Node... cars) {
        for (Node car : cars) {
            car.setTranslateX(0);
            car.setTranslateY(0);
        }
    }
    
    /**'
     * Divides the duration of each step by the simulation speed slider.
     * @return The duration of each step. 
     */
    private Duration stepDuration() {
        double speed = Math.max(0.05, simulationSlider.getValue()); 
        return Duration.millis(50 / speed);
    }

}
