package fr.polytech;

import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Line2D;

public class CanvaShape {
    int id;
    Object shape;
    Point3D firstPoint;
    Point3D secondPoint;

    public CanvaShape(int id, Object shape, Point3D firstPoint, Point3D secondPoint) {
        this.id = id;
        this.shape = shape;
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    public void updateShape(Point3D a, Point3D b) {
        this.firstPoint = a;
        this.secondPoint = b;
        if (shape instanceof Line2D) {
            Line2D line = (Line2D) shape;
            line.setLine(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY());
        }
    }

    public void drawingShape(GraphicsContext gc) {
        if (shape instanceof Line2D) {
            Line2D line = (Line2D) shape;
            drawingLine(gc, firstPoint, secondPoint);
        }
    }

    private void drawingLine(GraphicsContext gc, Point3D a, Point3D b) {
        gc.beginPath();
        gc.moveTo(a.getX(), a.getY());
        gc.lineTo(b.getX(), b.getY());
        gc.stroke();
    }
}
