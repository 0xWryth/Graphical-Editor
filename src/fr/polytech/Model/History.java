package fr.polytech.Model;

import fr.polytech.Model.Tasks.*;

import java.util.ArrayList;

public class History {
    private ArrayList<Task> history;
    private ArrayList<Task> redoTasks;

    public History() {
        history = new ArrayList<>();
        redoTasks = new ArrayList<>();
    }

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

    public void clone(Object[] data) {
        Task t = new Clone();
        t.execute(data);
        if (data.length >= 3) {
            Integer i = (Integer) data[2];
            addTaskToHistory(t, i);
        }
    }

    public void adding(Object[] data) {
        Task t = new Adding();
        t.execute(data);
        if (data.length >= 3) {
            Integer i = (Integer) data[2];
            addTaskToHistory(t, i);
        }
    }

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
