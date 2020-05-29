package fr.polytech;

import fr.polytech.Tasks.*;

import java.util.ArrayList;

public class History {
    private ArrayList<Task> history;

    public History() {
        history = new ArrayList<>();
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
                history.remove(history.size() - 1);
                return res;
            }
            catch (Exception e) {
                return new ArrayList<CanvaShape>();
            }
        }
        return new ArrayList<CanvaShape>();
    }
}
