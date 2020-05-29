package fr.polytech.Model.Tasks;

import fr.polytech.Model.CanvaShape;

import java.util.ArrayList;

public interface Task {
    /**
     * Task execution
     * @param data
     */
    public void execute(Object[] data);

    // Getters

    /**
     * Getting task id
     * @return
     */
    public String getId();

    /**
     * Getting tasks type
     * @return
     */
    public String getType();

    /**
     * Undoing a previous action
     * @return
     */
    public ArrayList<CanvaShape> undo();

    /**
     * Redoing an undo action
     * @return
     */
    public ArrayList<CanvaShape> redo();
}
