package fr.polytech.Tasks;

import fr.polytech.CanvaShape;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Adding implements Task{
    public int id;
    ArrayList<CanvaShape> oldCanvaObj = new ArrayList<CanvaShape>();
    ArrayList<CanvaShape> canvaObj;

    @Override
    public void execute(Object[] data) {
        // data[0] : type of task
        // data[1] : list of objects
        // data[2] : last added id
        // data[3] : mode
        // data[4] : firstPoint
        // data[5] : secondPoint
        // data[6] : fillingColor
        try {
            if (data.length == 0) {
                throw new Exception("You must provide some cloning data");
            } else if (data.length != 7) {
                throw new Exception("Not the good number of params");
            } else if (!((data[0] instanceof String) && (data[1] instanceof ArrayList) && (data[2] instanceof Integer) &&
                    (data[3] instanceof String) && (data[4] instanceof Point3D) && (data[5] instanceof Point3D) &&
                    data[6] instanceof Color)) {
                throw new Exception("Adding Tasks params are wrong");
            } else if (!data[0].equals("adding")) {
                throw new Exception("You can only provide an adding task");
            }

            canvaObj = (ArrayList<CanvaShape>) data[1];
            for (CanvaShape canvaShape : canvaObj) {
                oldCanvaObj.add(canvaShape);
            }
            Integer lastDrawnId = (Integer) data[2];
            String mode = (String) data[3];
            Point3D firstPoint = (Point3D) data[4];
            Point3D secondPoint = (Point3D) data[5];
            Color filingColor = (Color) data[6];

            id = lastDrawnId;

            // If the captured object is already added
            if (canvaObj.size() - 1 == lastDrawnId) {
                canvaObj.get(lastDrawnId).updatePoints(firstPoint, secondPoint);
            }
            else {
                String shape = mode.equals("rectangleRadio") ? "rectangle" :
                        mode.equals("lineRadio") ? "line" :
                                mode.equals("ellipseRadio") ? "ellipse" : "";
                if (!shape.equals(""))
                {
                    CanvaShape cs = new CanvaShape(lastDrawnId, shape, firstPoint, secondPoint, filingColor);
                    canvaObj.add(cs);
                }
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
        return id + "A";
    }

    @Override
    public String getType() {
        return "adding";
    }
}
