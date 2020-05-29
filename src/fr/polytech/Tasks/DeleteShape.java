package fr.polytech.Tasks;

import fr.polytech.CanvaShape;

import java.util.ArrayList;

public class DeleteShape implements Task {
    @Override
    public void execute(Object[] data) {

    }

    @Override
    public ArrayList<CanvaShape> undo() {
        return null;
    }

    @Override
    public void redo() {

    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getType() {
        return "deleting";
    }
}
