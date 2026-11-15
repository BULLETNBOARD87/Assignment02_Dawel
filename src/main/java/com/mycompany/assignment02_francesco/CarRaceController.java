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
import javafx.scene.Group;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author 2548213
 */
public class CarRaceController implements Initializable {
    private boolean isRaceOver = false;
    private boolean isStopped = false; 
    private final List<TranslateTransition> activeAnimations = new ArrayList<>();


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
    }    
    
    @FXML
    private void startSimulation(ActionEvent event) {
        if (!activeAnimations.isEmpty() && !isStopped) {
            return;
        }

        isStopped = false;
        isRaceOver = false;
        activeAnimations.clear();

        Random random = new Random();
        int min = 1;
        int max = 10;

        startRaceAnimation(car01, random, min, max);
        startRaceAnimation(car02, random, min, max);
        startRaceAnimation(car03, random, min, max);
        startRaceAnimation(car04, random, min, max);
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
        resetRacePositions(car01, car02, car03, car04);
    }
    
    public void startRaceAnimation(Node car, Random random, int min, int max) {
        int randomRange = random.nextInt((max - min) + 1) + min;

        TranslateTransition transition = new TranslateTransition(Duration.millis(50), car);
        transition.setByX(randomRange);
        transition.setCycleCount(1);

        activeAnimations.add(transition);

        transition.setOnFinished(e -> {
            if (isRaceOver || isStopped) { 
                return;
            }

            TranslateTransition activeTransition = (TranslateTransition) e.getSource();
            double currentX = car.getLayoutX() + car.getTranslateX();

            if (currentX >= 540) {
                isRaceOver = true; 

                activeTransition.stop(); 
                System.out.println(car.getId() + " HAS WON THE RACE!");

                for (TranslateTransition t : activeAnimations) {
                    t.stop();
                }

                activeAnimations.clear(); 

            } else {
               
                if (!isRaceOver) {
                    int nextStep = random.nextInt((max - min) + 1) + min;
                    activeTransition.setByX(nextStep);
                    activeTransition.play();
                }
            }
        });

        transition.play();
    }
    
    public void resetRacePositions(Node... cars) {
        isRaceOver = false;
        activeAnimations.clear();

        for (Node car : cars) {
            car.setTranslateX(0);
            car.setTranslateY(0); 
        }
    }

}
