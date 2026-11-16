/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.assignment02_francesco;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
/**
 * FXML Controller class
 *
 * @author super
 */
public class SlideshowController implements Initializable {


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
        // TODO
    }    
    
    @FXML
    private void skip(ActionEvent event) {
    }

}
