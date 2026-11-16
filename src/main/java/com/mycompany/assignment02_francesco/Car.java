/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment02_francesco;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

/**
 *
 * @author super
 */
public class Car extends Group {

    private static final double WHEEL_RADIUS = 9;

    private final int number;
    private final Color color;
    private final Rotate frontWheelRotation;
    private final Rotate rearWheelRotation;

    public Car(int number, Color color) {
        this.number = number;
        this.color = color;

        Polygon body = new Polygon(
                0, 42,  0, 28,  20, 22,  80, 22,  100, 28,  100, 42);
        body.setFill(color);
        body.setStroke(Color.BLACK);

        Polygon roof = new Polygon(
                25, 22,  35, 5,  70, 5,  80, 22);
        roof.setFill(color.darker());
        roof.setStroke(Color.BLACK);

        Rectangle window1 = new Rectangle(38, 9, 14, 11);
        Rectangle window2 = new Rectangle(56, 9, 14, 11);
        for (Rectangle w : new Rectangle[]{window1, window2}) {
            w.setFill(Color.LIGHTBLUE);
            w.setStroke(Color.BLACK);
        }

        Text numberText = new Text(44, 38, String.valueOf(number));
        numberText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        numberText.setFill(Color.WHITE);
        numberText.setStroke(Color.BLACK);
        numberText.setStrokeWidth(0.5);

        rearWheelRotation = new Rotate(0, 22, 42);
        frontWheelRotation = new Rotate(0, 78, 42);
        Group rearWheel = createWheel(22, 42, rearWheelRotation);
        Group frontWheel = createWheel(78, 42, frontWheelRotation);

        getChildren().addAll(roof, body, window1, window2, numberText, rearWheel, frontWheel);

        translateXProperty().addListener((obs, oldX, newX) -> {
            double distance = newX.doubleValue() - oldX.doubleValue();
            double degrees = Math.toDegrees(distance / WHEEL_RADIUS);
            rearWheelRotation.setAngle(rearWheelRotation.getAngle() + degrees);
            frontWheelRotation.setAngle(frontWheelRotation.getAngle() + degrees);
        });
    }

    /**
     * Creates a wheel and a line that spins. 
     * @param cx center of the wheel's X position.
     * @param cy center of the wheel's Y position.
     * @param rotation a rotation animation.
     * @return 
     */
    private Group createWheel(double cx, double cy, Rotate rotation) {
        Circle tire = new Circle(cx, cy, WHEEL_RADIUS, Color.DARKSLATEGRAY);
        tire.setStroke(Color.BLACK);

        Line spoke = new Line(cx, cy, cx + WHEEL_RADIUS, cy);
        spoke.setStroke(Color.WHITE);
        spoke.setStrokeWidth(2);

        Group wheel = new Group(tire, spoke);
        wheel.getTransforms().add(rotation);
        return wheel;
    }

    /**
     * Resets the car and wheels back to the start line and without rotation.
     */
    public void reset() {
        setTranslateX(0);
        setTranslateY(0);
        rearWheelRotation.setAngle(0);
        frontWheelRotation.setAngle(0);
    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }
}
