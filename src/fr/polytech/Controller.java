package fr.polytech;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.awt.geom.Line2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ToggleGroup control;

    @FXML
    private Canvas canvas;

    private String mode = "";
    private boolean captureMousePos = false;
    private Point3D firstPoint;
    private Point3D secondPoint;

    private ArrayList<Object> canvaObj = new ArrayList<Object>();
    private int lastDrawnId = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setMode(ActionEvent actionEvent) {
        RadioButton rb = (RadioButton) control.getSelectedToggle();
        this.mode = rb.getId();
        System.out.println(this.mode);
    }

    public void mousePressed(MouseEvent mouseEvent) {
        this.captureMousePos = true;
        System.out.println("capturing mouse pos");
        firstPoint = new Point3D(mouseEvent.getX(), mouseEvent.getY(), 0);
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        System.out.println("end of capture of mouse pos");
        this.lastDrawnId++;
        this.captureMousePos = false;
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        secondPoint = new Point3D(mouseEvent.getX(), mouseEvent.getY(), 0);

        addingLineShape();
        drawing();
        System.out.println(canvaObj.size());
    }

    private void drawing() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Reseting canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Object o : canvaObj) {
            if (o instanceof Line2D) {
                Line2D line = (Line2D) o;
                drawingLine(new Point3D(line.getX1(), line.getY1(), 0), new Point3D(line.getX2(), line.getY2(), 0));
            }
        }
    }

    private void drawingLine(Point3D a, Point3D b) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.beginPath();
        gc.moveTo(a.getX(), a.getY());
        gc.lineTo(b.getX(), b.getY());
        gc.stroke();
    }

    private void addingLineShape() {
        if (this.captureMousePos) {
            // If the captured object is already added
            if (canvaObj.size() - 1 == this.lastDrawnId) {
                Line2D.Double newLine = new Line2D.Double(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY());
                this.canvaObj.set(lastDrawnId, newLine);
            }
            else {
                this.canvaObj.add(new Line2D.Double(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY()));
            }
        }
    }
}
