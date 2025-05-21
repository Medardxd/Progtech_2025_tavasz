package com.carrental.auth;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuControllerTest {
    @Test
    void testCalculateTotal_noExtras() {
        MainMenuController ctrl = new MainMenuController();
        LocalDate start = LocalDate.of(2025, 5, 21);
        LocalDate end = LocalDate.of(2025, 5, 23);

        double total = ctrl.calculateTotal(start, end, 20.0, false, false);
        assertEquals(60.0, total); // 3 nap * 20 = 60
    }

    @Test
    void testCalculateTotal_withExtras() {
        MainMenuController ctrl = new MainMenuController();
        LocalDate start = LocalDate.of(2025, 5, 21);
        LocalDate end = LocalDate.of(2025, 5, 23);

        double total = ctrl.calculateTotal(start, end, 20.0, true, true);
        assertEquals(85.0, total);
    }

    @Test
    void testCalculateTotal_invalidDates() {
        MainMenuController ctrl = new MainMenuController();
        LocalDate start = LocalDate.of(2025, 5, 23);
        LocalDate end = LocalDate.of(2025, 5, 21);

        double total = ctrl.calculateTotal(start, end, 20.0, false, false);
        assertEquals(0.0, total);
    }

    @Test
    void testCalculateTotal_sameDay() {
        MainMenuController ctrl = new MainMenuController();
        LocalDate date = LocalDate.of(2025, 5, 21);

        double total = ctrl.calculateTotal(date, date, 20.0, true, false);
        assertEquals(30.0, total);
    }

}