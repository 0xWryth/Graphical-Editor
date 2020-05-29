package fr.polytech;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.ArrayList;
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

    private ArrayList<CanvaShape> canvaObj = new ArrayList<CanvaShape>();
    private int lastDrawnId = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

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

        addingShape();

        drawing();
    }

    private void drawing() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Reseting canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (CanvaShape o : canvaObj) {
            o.drawingShape(gc);
        }
    }

    private void addingShape() {
        if (this.captureMousePos) {
            // If the captured object is already added
            if (canvaObj.size() - 1 == this.lastDrawnId) {
                this.canvaObj.get(lastDrawnId).updatePoints(firstPoint, secondPoint);
            }
            else {
                String shape = this.mode.equals("rectangleRadio") ? "rectangle" : this.mode.equals("lineRadio") ? "line" : "";
                if (!shape.equals(""))
                {
                    CanvaShape cs = new CanvaShape(lastDrawnId, shape, firstPoint, secondPoint);
                    this.canvaObj.add(cs);
                }
            }
        }
    }
}
