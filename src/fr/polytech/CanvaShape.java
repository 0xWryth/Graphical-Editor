package fr.polytech;

import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public class CanvaShape {
    int id;
    String shape;
    Point3D firstPoint;
    Point3D secondPoint;
    double difX = 0, difY = 0;
    Color filingColor;
    boolean selected = false;

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
        final double lowerX = Math.min(firstPoint.getX() - difX, secondPoint.getX() - difX);
        final double higherX = Math.max(firstPoint.getX() - difX, secondPoint.getX() - difX);
        final double lowerY = Math.min(firstPoint.getY() - difY, secondPoint.getY() - difY);
        final double higherY = Math.max(firstPoint.getY() - difY, secondPoint.getY() - difY);

        Point3D A = new Point3D(lowerX, higherY, 0);
        Point3D B = new Point3D(higherX, higherY, 0);
        Point3D C = new Point3D(higherX, lowerY, 0);
        Point3D D = new Point3D(lowerX, lowerY, 0);

        Color def = (Color) gc.getFill();
        gc.setFill(this.filingColor);
        gc.fillRoundRect(lowerX, lowerY, higherX - lowerX, higherY - lowerY, 0, 0);
        gc.setFill(def);

        Color c = this.selected ? Color.BLUE : Color.BLACK;

        drawingLine(gc, A, B, c);
        drawingLine(gc, B, C, c);
        drawingLine(gc, C, D, c);
        drawingLine(gc, D, A, c);
    }

    private void drawingEllipse(GraphicsContext gc) {
        final double lowerX = Math.min(firstPoint.getX() - difX, secondPoint.getX() - difX);
        final double higherX = Math.max(firstPoint.getX() - difX, secondPoint.getX() - difX);

        Color def = (Color) gc.getFill();
        gc.setFill(this.filingColor);
        gc.fillOval(firstPoint.getX() - difX, firstPoint.getY() - difY, higherX-lowerX, higherX-lowerX);
        gc.setFill(def);

        gc.strokeOval(firstPoint.getX() - difX, firstPoint.getY() - difY, higherX-lowerX, higherX-lowerX);
    }

    public void selectShape(GraphicsContext gc, Point3D point) {
        if (this.shape.equals("rectangle")) {
            final double lowerX = Math.min(firstPoint.getX() - difX, secondPoint.getX() - difX);
            final double higherX = Math.max(firstPoint.getX() - difX, secondPoint.getX() - difX);
            final double lowerY = Math.min(firstPoint.getY() - difY, secondPoint.getY() - difY);
            final double higherY = Math.max(firstPoint.getY() - difY, secondPoint.getY() - difY);

            if (point.getX() >= lowerX && point.getX() <= higherX &&
                point.getY() >= lowerY && point.getY() <= higherY) {
                this.selected = !this.selected;
            }
        }

        drawingShape(gc);
    }

    public void moveShape(GraphicsContext gc, double difX, double difY) {
        if (this.selected) {
            this.difX = difX;
            this.difY = difY;
        }
    }
}
