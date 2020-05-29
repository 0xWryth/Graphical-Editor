package fr.polytech.Model.Tasks;

import fr.polytech.Model.CanvaShape;

import java.util.ArrayList;

public class Move implements Task {
    @Override
    public void execute(Object[] data) {

    }

    @Override
    public ArrayList<CanvaShape> undo() {
        return null;
    }

    @Override
    public ArrayList<CanvaShape> redo() {
        return null;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getType() {
        return "moving";
    }
}
