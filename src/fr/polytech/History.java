package fr.polytech;

import fr.polytech.Tasks.Adding;
import fr.polytech.Tasks.Clone;
import fr.polytech.Tasks.Task;

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
            String id = i.toString() + (t.getType().equals("adding") ? "A" : t.getType().equals("cloning") ? "C" : "");
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
