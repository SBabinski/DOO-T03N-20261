package com.weatherapp;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class WeatherApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // fallback to default
            }
            WeatherFrame frame = new WeatherFrame();
            frame.setVisible(true);
        });
    }
}
