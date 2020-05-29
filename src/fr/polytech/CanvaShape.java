package fr.polytech;

import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public class CanvaShape {
    int id;
    String shape;
    Point3D firstPoint;
    Point3D secondPoint;
    Color filingColor;

    public CanvaShape(int id, String shape, Point3D firstPoint, Point3D secondPoint, Color filingColor) {
        this.id = id;
        this.shape = shape;
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.filingColor = filingColor;
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
        drawingLine(gc, firstPoint, secondPoint, filingColor);
    }

    private void drawingLine(GraphicsContext gc, Point3D a, Point3D b, Color strokeColor) {
        gc.beginPath();
        gc.setStroke(strokeColor);
        gc.moveTo(a.getX(), a.getY());
        gc.lineTo(b.getX(), b.getY());
        gc.stroke();
        gc.setStroke(Color.BLACK);
    }

    private void drawingRectangle(GraphicsContext gc) {
        final double lowerX = Math.min(firstPoint.getX(), secondPoint.getX());
        final double higherX = Math.max(firstPoint.getX(), secondPoint.getX());
        final double lowerY = Math.min(firstPoint.getY(), secondPoint.getY());
        final double higherY = Math.max(firstPoint.getY(), secondPoint.getY());

        Point3D A = new Point3D(lowerX, higherY, 0);
        Point3D B = new Point3D(higherX, higherY, 0);
        Point3D C = new Point3D(higherX, lowerY, 0);
        Point3D D = new Point3D(lowerX, lowerY, 0);

        Color def = (Color) gc.getFill();
        gc.setFill(this.filingColor);
        gc.fillRoundRect(lowerX, lowerY, higherX - lowerX, higherY - lowerY, 0, 0);
        gc.setFill(def);

        drawingLine(gc, A, B, Color.BLACK);
        drawingLine(gc, B, C, Color.BLACK);
        drawingLine(gc, C, D, Color.BLACK);
        drawingLine(gc, D, A, Color.BLACK);
    }

    private void drawingEllipse(GraphicsContext gc) {
        final double lowerX = Math.min(firstPoint.getX(), secondPoint.getX());
        final double higherX = Math.max(firstPoint.getX(), secondPoint.getX());

        Color def = (Color) gc.getFill();
        gc.setFill(this.filingColor);
        gc.fillOval(firstPoint.getX(), firstPoint.getY(), higherX-lowerX, higherX-lowerX);
        gc.setFill(def);

        gc.strokeOval(firstPoint.getX(), firstPoint.getY(), higherX-lowerX, higherX-lowerX);
    }
}
