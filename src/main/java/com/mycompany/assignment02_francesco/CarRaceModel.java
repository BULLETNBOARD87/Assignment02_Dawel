/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment02_francesco;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author 2548213
 */
public class CarRaceModel {
    
    private final TextArea resultsArea;
    private final List<Car> finishOrder = new ArrayList<>();
    
    private static final Map<String, Color> KNOWN_COLORS = new LinkedHashMap<>();
    static {
        KNOWN_COLORS.put("Red", Color.RED);
        KNOWN_COLORS.put("Blue", Color.BLUE);
        KNOWN_COLORS.put("Green", Color.GREEN);
        KNOWN_COLORS.put("Yellow", Color.YELLOW);
        KNOWN_COLORS.put("Orange", Color.ORANGE);
        KNOWN_COLORS.put("Purple", Color.PURPLE);
        KNOWN_COLORS.put("Pink", Color.PINK);
        KNOWN_COLORS.put("Black", Color.BLACK);
        KNOWN_COLORS.put("White", Color.WHITE);
        KNOWN_COLORS.put("Gray", Color.GRAY);
        KNOWN_COLORS.put("Brown", Color.BROWN);
        KNOWN_COLORS.put("Cyan", Color.CYAN);
        KNOWN_COLORS.put("Magenta", Color.MAGENTA);
    }

   
    public CarRaceModel(TextArea resultsArea) {
        this.resultsArea = resultsArea;
        this.resultsArea.setEditable(false);
    }
    
    /**
     * Records a car passing through the finish line.
     * @param car The car that passes the finish line.
     * @return Returns true if it was the first one to do it.
     */
    public boolean recordFinish(Car car) {
        if (finishOrder.contains(car)) {
            return false;
        }
        finishOrder.add(car);
        return finishOrder.size() == 1;
    }
    
    public boolean isFinished(Car car) {
        return finishOrder.contains(car);
    }

    public boolean allFinished(int totalCars) {
        return finishOrder.size() >= totalCars;
    }
    
    /**
     * Announces the winner inside of the results text area
     */
    public void announceWinner() {
        if (finishOrder.isEmpty()) {
            return;
        }
        Car winner = finishOrder.get(0);
        resultsArea.setText("CONGRATULATIONS!\n"
                + describe(winner) + " wins the race!\n");
    }
    
    public void showFinalResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nFINAL RESULTS\n");
        sb.append("-------------\n");
        for (int i = 0; i < finishOrder.size(); i++) {
            sb.append(ordinal(i + 1)).append(": ")
              .append(describe(finishOrder.get(i))).append("\n");
        }
        resultsArea.appendText(sb.toString());
    }

    public void reset() {
        finishOrder.clear();
        resultsArea.clear();
    }
    
    private String describe(Car car) {
        return "Car " + car.getNumber() + " (" + colorName(car.getColor()) + ")";
    }
    
    private int carNumber(Car car) {
        String digits = car.getId() == null ? "" : car.getId().replaceAll("\\D+", "");
        return digits.isEmpty() ? 0 : Integer.parseInt(digits);
    }
    
    private String colorName(Paint paint) {
        if (!(paint instanceof Color)) {
            return "Unknown";
        }
        Color c = (Color) paint;
        String best = "Unknown";
        double bestDist = Double.MAX_VALUE;
        for (Map.Entry<String, Color> entry : KNOWN_COLORS.entrySet()) {
            Color k = entry.getValue();
            double dr = c.getRed() - k.getRed();
            double dg = c.getGreen() - k.getGreen();
            double db = c.getBlue() - k.getBlue();
            double dist = dr * dr + dg * dg + db * db;
            if (dist < bestDist) {
                bestDist = dist;
                best = entry.getKey();
            }
        }
        return best;
    }
    
    private String ordinal(int n) {
        switch (n) {
            case 1: return "1st";
            case 2: return "2nd";
            case 3: return "3rd";
            default: return n + "th";
        }
    }
    
}
