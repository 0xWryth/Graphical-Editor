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

    public void clone(Object[] data) {
        Task t = new Clone();
        t.execute(data);
        history.add(t);
    }

    public void adding(Object[] data) {
        Task t = new Adding();
        t.execute(data);
        history.add(t);
    }

    public ArrayList<CanvaShape> undo() {
        if (history.size() > 0) {
            return history.get(history.size() - 1).undo();
        }
        return null;
    }
}
