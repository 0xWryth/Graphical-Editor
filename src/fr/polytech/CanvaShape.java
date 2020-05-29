package fr.polytech;

import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class CanvaShape {
    int id;
    String shape;
    Point3D firstPoint;
    Point3D secondPoint;

    public CanvaShape(int id, String shape, Point3D firstPoint, Point3D secondPoint) {
        this.id = id;
        this.shape = shape;
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    public void updatePoints(Point3D a, Point3D b) {
        this.firstPoint = a;
        this.secondPoint = b;
    }

    public void drawingShape(GraphicsContext gc) {
        if (shape.equals("line")) {
            drawingLine(gc);
        }
        else if (shape.equals("rectangle")) {
            drawingRectangle(gc);
        }
        else if (shape.equals("ellipse")) {
            drawingEllipse(gc);
        }
    }

    private void drawingLine(GraphicsContext gc) {
        drawingLine(gc, firstPoint, secondPoint);
    }

    private void drawingLine(GraphicsContext gc, Point3D a, Point3D b) {
        gc.beginPath();
        gc.moveTo(a.getX(), a.getY());
        gc.lineTo(b.getX(), b.getY());
        gc.stroke();
    }

    private void drawingRectangle(GraphicsContext gc) {
        Point3D upperRight = new Point3D(secondPoint.getX(), firstPoint.getY(), 0);
        Point3D lowerLeft = new Point3D(firstPoint.getX(), secondPoint.getY(), 0);
        drawingLine(gc, firstPoint, upperRight);
        drawingLine(gc, upperRight, secondPoint);
        drawingLine(gc, firstPoint, lowerLeft);
        drawingLine(gc, lowerLeft, secondPoint);
    }

    private void drawingEllipse(GraphicsContext gc) {
        final double lowerX = Math.min(firstPoint.getX(), secondPoint.getX());
        final double higherX = Math.max(firstPoint.getX(), secondPoint.getX());

        gc.strokeOval(firstPoint.getX(), firstPoint.getY(), higherX-lowerX, higherX-lowerX);
    }
}
