package fr.polytech.Controller;

import fr.polytech.Model.CanvaShape;
import fr.polytech.Model.History;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    // FXML elements
    @FXML
    private ToggleGroup control;

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    // Controller data
    // > Radiobutton data
    private String mode = "";

    // > Mouse & Point position
    private boolean captureMousePos = false;
    private Point3D firstPoint;
    private Point3D secondPoint;

    // > Color data
    private Color filingColor;

    // > Main application data
    private ArrayList<CanvaShape> canvaObj = new ArrayList<CanvaShape>();
    private Integer lastDrawnId = 0;
    private History history = new History();

    /**
     * Controller initialization
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filingColor = colorPicker.getValue();
    }

    // Handlers
    // > KeyBoard Handler
    /**
     * Key Binding function to handle undo/redo with CTRL+Z & CTRL+Y
     * @param keyEvent
     */
    public void keyTyped(KeyEvent keyEvent) {
        // History undo
        if (keyEvent.getCode() == KeyCode.Z && keyEvent.isControlDown()) {
            System.out.println("CTRL + Z !");
            canvaObj = history.undo();
            drawing();
        }
        // History redo
        else if (keyEvent.getCode() == KeyCode.Y && keyEvent.isControlDown()) {
            System.out.println("CTRL + Y !");
            canvaObj = history.redo();
            drawing();
        }
    }

    // > RadioButton Handlers
    /**
     * Defining what tool the user is being using (mouve, rectangle, line...)
     * @param actionEvent
     */
    public void setMode(ActionEvent actionEvent) {
        RadioButton rb = (RadioButton) control.getSelectedToggle();
        this.mode = rb.getId();
        System.out.println(this.mode);
    }

    // > Mouse Handler
    /**
     * Getting first canva point drawing position / Selecting some shape
     * @param mouseEvent
     */
    public void mousePressed(MouseEvent mouseEvent) {
        this.captureMousePos = true;
        System.out.println("capturing mouse pos");
        firstPoint = new Point3D(mouseEvent.getX(), mouseEvent.getY(), 0);

        if (this.mode.equals("selectMoveRadio")) {
            for (CanvaShape o : canvaObj) {
                o.selectShape(canvas.getGraphicsContext2D(), firstPoint);
            }
        }
    }

    /**
     * Capturing second canva point drawing position / Moving some shape
     * @param mouseEvent
     */
    public void mouseReleased(MouseEvent mouseEvent) {
        System.out.println("end of capture of mouse pos");
        this.lastDrawnId++;
        this.captureMousePos = false;

        if (!this.mode.equals("selectMoveRadio")) {
            for (CanvaShape o : canvaObj) {
                o.updateDif();
            }
            drawing();
        }
    }

    /**
     * Capturing second canva point during mouse movement for previewing the new shape
     * @param mouseEvent
     */
    public void mouseDragged(MouseEvent mouseEvent) {
        secondPoint = new Point3D(mouseEvent.getX(), mouseEvent.getY(), 0);

        movingShape();
        addingShape();
        drawing();
    }

    // > Canva drawing function

    /**
     * Drawing the app shape on the canva
     */
    private void drawing() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Reseting canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (CanvaShape o : canvaObj) {
            if (o != null) {
                o.drawingShape(gc);
            }
        }
    }

    // > Shape functions

    /**
     * Adding a shape to the app
     */
    private void addingShape() {
        if (this.captureMousePos) {
            Object[] data = {"adding", this.canvaObj, this.lastDrawnId, this.mode, firstPoint, secondPoint, filingColor};
            history.adding(data);
        }
    }

    /**
     * Moving the selected shape with cursor dif position
     */
    private void movingShape() {
        double difX = firstPoint.getX() - secondPoint.getX();
        double difY = firstPoint.getY() - secondPoint.getY();

        for (CanvaShape o : canvaObj) {
            o.moveShape(canvas.getGraphicsContext2D(), difX, difY);
        }
    }

    /**
     * Deleting the selected shapes
     * @param actionEvent
     */
    public void deleteShape(ActionEvent actionEvent) {
        Object[] data = {"deleting", this.canvaObj, this.lastDrawnId, this.mode, firstPoint, secondPoint};
        this.canvaObj = history.deleting(data);
        drawing();
    }

    /**
     * Cloning selected shapes
     * @param actionEvent
     */
    public void cloneShape(ActionEvent actionEvent) {
        Object[] data = {"cloning", canvaObj, lastDrawnId};
        history.clone(data);
        drawing();
    }

    /**
     * Changing the color of a shape / future shape
     * @param actionEvent
     */
    public void picker(ActionEvent actionEvent) {
        this.filingColor = colorPicker.getValue();
        System.out.println("Switching to : " + colorPicker.getValue());

        Object[] data = {"colorChanging", this.canvaObj, this.lastDrawnId, canvas.getGraphicsContext2D(), this.filingColor};
        this.canvaObj = history.colorChanging(data);
        drawing();
    }
}
