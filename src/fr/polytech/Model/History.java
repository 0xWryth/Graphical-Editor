package fr.polytech.Model;

import fr.polytech.Model.Tasks.*;

import java.util.ArrayList;

public class History {
    private ArrayList<Task> history;
    private ArrayList<Task> redoTasks;

    /**
     * History constructor
     */
    public History() {
        history = new ArrayList<>();
        redoTasks = new ArrayList<>();
    }

    // Methods

    /**
     * Adding / Modifying a task in the history
     * @param t the task to add
     * @param i some shape id
     */
    private void addTaskToHistory(Task t, Integer i) {
        Integer found = null;
        int index = 0;
        for (Task task : history) {
            String id = i.toString() + (t.getType().equals("adding") ? "A" : t.getType().equals("cloning") ? "C" :
                    t.getType().equals("deleting") ? "D" : t.getType().equals("colorChanging") ? "K" : "");
            if (task.getId().equals(id)) {
                found = index;
            }
            index++;
        }
        if (found == null) {
            history.add(t);
        } else {
            history.set(found, t);
        }
        redoTasks = new ArrayList<>();
    }

    // Tasks

    /**
     * Cloning some shape
     * @param data
     */
    public void clone(Object[] data) {
        Task t = new Clone();
        t.execute(data);
        if (data.length >= 3) {
            Integer i = (Integer) data[2];
            addTaskToHistory(t, i);
        }
    }

    /**
     * Adding a new shape
     * @param data
     */
    public void adding(Object[] data) {
        Task t = new Adding();
        t.execute(data);
        if (data.length >= 3) {
            Integer i = (Integer) data[2];
            addTaskToHistory(t, i);
        }
    }

    /**
     * Deleting a shaoe
     * @param data
     * @return new app data
     */
    public ArrayList<CanvaShape> deleting(Object[] data) {
        Task t = new DeleteShape();
        t.execute(data);
        if (data.length >= 3) {
            Integer i = (Integer) data[2];
            addTaskToHistory(t, i);
            return ((DeleteShape) t).getCanvaObj();
        }
        return new ArrayList<CanvaShape>();
    }

    /**
     * Changing a shape color
     * @param data
     * @return new app data
     */
    public ArrayList<CanvaShape> colorChanging(Object[] data) {
        Task t = new ColorChange();
        t.execute(data);
        if (data.length >= 3) {
            Integer i = (Integer) data[2];
            addTaskToHistory(t, i);
            return ((ColorChange) t).getCanvaObj();
        }
        return new ArrayList<CanvaShape>();
    }

    // Control functions

    /**
     * Undoing previous action
     * @return new app objects data
     */
    public ArrayList<CanvaShape> undo() {
        if (history.size() > 0) {
            System.out.println(history.size());
            try {
                Task t = history.get(history.size() - 1);
                ArrayList<CanvaShape> res = t.undo();
                redoTasks.add(t);
                history.remove(history.size() - 1);
                return res;
            }
            catch (Exception e) {
                return new ArrayList<CanvaShape>();
            }
        }
        return new ArrayList<CanvaShape>();
    }

    /**
     * Redoing last action which had been undo
     * @return new app objects data
     */
    public ArrayList<CanvaShape> redo() {
        if (redoTasks.size() > 0) {
            try {
                Task t = redoTasks.get(0);
                ArrayList<CanvaShape> res = t.redo();
                history.add(t);
                redoTasks.remove(0);
                return res;
            }
            catch (Exception e) {
                return new ArrayList<CanvaShape>();
            }
        }
        return new ArrayList<CanvaShape>();
    }
}
