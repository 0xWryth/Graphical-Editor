package fr.polytech;

import fr.polytech.Tasks.Adding;
import fr.polytech.Tasks.Clone;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ToggleGroup control;

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    private String mode = "";
    private boolean captureMousePos = false;
    private Point3D firstPoint;
    private Point3D secondPoint;
    private Color filingColor;

    private ArrayList<CanvaShape> canvaObj = new ArrayList<CanvaShape>();
    private Integer lastDrawnId = 0;

    private History history = new History();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filingColor = colorPicker.getValue();
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
        if (this.mode.equals("selectMoveRadio")) {
            for (CanvaShape o : canvaObj) {
                o.selectShape(canvas.getGraphicsContext2D(), firstPoint);
            }
        }
    }

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

    public void mouseDragged(MouseEvent mouseEvent) {
        secondPoint = new Point3D(mouseEvent.getX(), mouseEvent.getY(), 0);

        movingShape();
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
            Object[] data = {"adding", this.canvaObj, this.lastDrawnId, this.mode, firstPoint, secondPoint, filingColor};
            (new Adding()).execute(data);
        }
    }

    public void picker(ActionEvent actionEvent) {
        this.filingColor = colorPicker.getValue();
        System.out.println("Switching to : " + colorPicker.getValue());

        for (CanvaShape o : canvaObj) {
            o.setFilingColor(canvas.getGraphicsContext2D(), colorPicker.getValue());
        }
    }

    private void movingShape() {
        double difX = firstPoint.getX() - secondPoint.getX();
        double difY = firstPoint.getY() - secondPoint.getY();

        for (CanvaShape o : canvaObj) {
            o.moveShape(canvas.getGraphicsContext2D(), difX, difY);
        }
    }

    public void deleteShape(ActionEvent actionEvent) {
        ArrayList<Integer> toDelete = new ArrayList<Integer>();
        for (CanvaShape o : canvaObj) {
            if (o.isSelected()) {
                toDelete.add(o.getId());
            }
        }

        ArrayList<CanvaShape> newCanvaObj = new ArrayList<CanvaShape>();
        int i = 0;
        for (CanvaShape canvaShape : canvaObj) {
            boolean flag = false;
            int index = 0;
            while (flag == false && index < toDelete.size()) {
                if (canvaShape.getId() == toDelete.get(index)) {
                    flag = true;
                }
                index++;
            }

            if (!flag) {
                newCanvaObj.add(canvaShape);
            }
            i++;
        }

        this.canvaObj = newCanvaObj;
        drawing();
    }

    public void cloneShape(ActionEvent actionEvent) {
        Object[] data = {"cloning", canvaObj, lastDrawnId};
        history.clone(data);
        drawing();
    }

    public void keyTyped(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.Z && keyEvent.isControlDown()) {
            System.out.println("CTRL + Z !");
            canvaObj = history.undo();
            drawing();
        }
    }
}
