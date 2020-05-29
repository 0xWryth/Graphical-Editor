package fr.polytech;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ToggleGroup control;

    private String mode = "";
    private boolean captureMousePos = false;
    private Point3D firstPoint;
    private Point3D secondPoint;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMode(ActionEvent actionEvent) {
        RadioButton rb = (RadioButton) control.getSelectedToggle();
        this.mode = rb.getId();
        System.out.println(this.mode);
    }

    public void drag(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }

    public void dragEnter(MouseEvent mouseEvent) {
        captureMousePos = true;
        firstPoint = new Point3D(mouseEvent.getX(), mouseEvent.getY(), 0);
    }

    public void dragExit(MouseEvent mouseEvent) {
        captureMousePos = false;
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        secondPoint = new Point3D(mouseEvent.getX(), mouseEvent.getY(), 0);
    }
}
