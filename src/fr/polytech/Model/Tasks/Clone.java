package fr.polytech.Model.Tasks;

import fr.polytech.Model.CanvaShape;

import java.util.ArrayList;

public class Clone implements Task {
    public String id;
    private ArrayList<Integer> addedIndex = new ArrayList<Integer>();
    ArrayList<CanvaShape> oldCanvaObj = new ArrayList<>();
    ArrayList<CanvaShape> canvaObj;

    @Override
    public void execute(Object[] data) {
        // data[0] : type of task
        // data[1] : list of objects
        // data[2] : last added id

        int clonedId = 0;

        try {
            if (data.length == 0) {
                throw new Exception("You must provide some cloning data");
            }
            else if (data.length != 3){
                throw new Exception("Not the good number of params");
            }
            else if (!((data[0] instanceof String) && (data[1] instanceof ArrayList) && (data[2] instanceof Integer))) {
                throw new Exception("Cloning Tasks params are wrong");
            }
            else if (!data[0].equals("cloning")){
                throw new Exception("You can only provide a cloning task");
            }

            ArrayList<CanvaShape> futureCanvaObj = new ArrayList<CanvaShape>();
            canvaObj = (ArrayList<CanvaShape>) data[1];
            Integer lastDrawnId = (Integer) data[2];
            id = lastDrawnId.toString();
            for (CanvaShape canvaShape : canvaObj) {
                oldCanvaObj.add(canvaShape);
            }

            for (CanvaShape o : canvaObj) {
                if (o.isSelected()) {
                    CanvaShape cs = new CanvaShape(o, (Integer) data[2] + clonedId);
                    futureCanvaObj.add(cs);
                    clonedId++;
                }
            }

            data[2] = (Integer) data[2] + clonedId;

            canvaObj.addAll(futureCanvaObj);
            data[1] = canvaObj;
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public ArrayList<CanvaShape> undo() {
        return oldCanvaObj;
    }

    @Override
    public ArrayList<CanvaShape> redo() {
        return canvaObj;
    }

    @Override
    public String getId() {
        return id + "C";
    }

    @Override
    public String getType() {
        return "cloning";
    }
}
