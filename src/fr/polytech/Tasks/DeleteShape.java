package fr.polytech.Tasks;

import fr.polytech.CanvaShape;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class DeleteShape implements Task {
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
        try {
            if (data.length == 0) {
                throw new Exception("You must provide some deleting data");
            } else if (data.length != 6) {
                throw new Exception("Not the good number of params");
            } else if (!((data[0] instanceof String) && (data[1] instanceof ArrayList) && (data[2] instanceof Integer) &&
                    (data[3] instanceof String) && (data[4] instanceof Point3D) && (data[5] instanceof Point3D))) {
                throw new Exception("Deleting Tasks params are wrong");
            } else if (!data[0].equals("deleting")) {
                throw new Exception("You can only provide a deleting task");
            }

            canvaObj = (ArrayList<CanvaShape>) data[1];
            for (CanvaShape canvaShape : canvaObj) {
                oldCanvaObj.add(canvaShape);
            }

            Integer lastDrawnId = (Integer) data[2];
            String mode = (String) data[3];
            Point3D firstPoint = (Point3D) data[4];
            Point3D secondPoint = (Point3D) data[5];

            id = lastDrawnId;

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

            canvaObj = newCanvaObj;
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
        return id + "D";
    }

    @Override
    public String getType() {
        return "deleting";
    }

    public ArrayList<CanvaShape> getCanvaObj() {
        return canvaObj;
    }
}
