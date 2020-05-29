package fr.polytech.Tasks;

public interface Task {
    public void execute(Object[] data);
    public void undo();
    public void redo();
}
