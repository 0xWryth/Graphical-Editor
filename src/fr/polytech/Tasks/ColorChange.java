package fr.polytech.Tasks;

import fr.polytech.CanvaShape;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ColorChange implements Task {
    public int id;
    ArrayList<CanvaShape> oldCanvaObj = new ArrayList<CanvaShape>();
    ArrayList<CanvaShape> canvaObj;

    @Override
    public void execute(Object[] data) {
        // data[0] : type of task
        // data[1] : list of objects
        // data[2] : last added id
        // data[3] : gc
        // data[4] : fillingColor
        try {
            if (data.length == 0) {
                throw new Exception("You must provide some cloning data");
            } else if (data.length != 5) {
                throw new Exception("Not the good number of params");
            } else if (!((data[0] instanceof String) && (data[1] instanceof ArrayList) && (data[2] instanceof Integer)
                    && (data[3] instanceof GraphicsContext) && (data[4] instanceof Color))) {
                throw new Exception("Changing color Tasks params are wrong");
            }
            else if (!data[0].equals("colorChanging")) {
                throw new Exception("You can only provide a color changing task");
            }

            canvaObj = (ArrayList<CanvaShape>) data[1];
            for (CanvaShape canvaShape : canvaObj) {
                oldCanvaObj.add(canvaShape);
            }
            Integer lastDrawnId = (Integer) data[2];
            GraphicsContext gc = (GraphicsContext) data[3];
            Color filingColor = (Color) data[4];

            id = lastDrawnId;

            for (CanvaShape o : canvaObj) {
                o.setFilingColor(gc, filingColor);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public ArrayList<CanvaShape> undo() {
        return oldCanvaObj;
    }

    @Override
    public void redo() {

    }

    @Override
    public String getId() {
        return id + "K";
    }

    @Override
    public String getType() {
        return "colorChanging";
    }

    public ArrayList<CanvaShape> getCanvaObj() {
        return canvaObj;
    }
}
