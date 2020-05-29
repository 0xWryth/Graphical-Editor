package fr.polytech.Model.Tasks;

import fr.polytech.Model.CanvaShape;

import java.util.ArrayList;

public interface Task {
    public void execute(Object[] data);
    public ArrayList<CanvaShape> undo();
    public ArrayList<CanvaShape> redo();
    public String getId();
    public String getType();
}
