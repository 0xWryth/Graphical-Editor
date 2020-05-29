package fr.polytech.Tasks;

import fr.polytech.CanvaShape;

import java.util.ArrayList;

public interface Task {
    public void execute(Object[] data);
    public ArrayList<CanvaShape> undo();
    public void redo();
    public String getId();
    public String getType();
}
