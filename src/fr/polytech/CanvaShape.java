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

    private final static double clonedOffset = 10.0;

    public CanvaShape(int id, String shape, Point3D firstPoint, Point3D secondPoint, Color filingColor) {
        this.id = id;
        this.shape = shape;
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.filingColor = filingColor;
    }

    public CanvaShape(CanvaShape cs, int id) {
        this(id, cs.shape, new Point3D(cs.firstPoint.getX() + clonedOffset, cs.firstPoint.getY() + clonedOffset, 0),
                new Point3D(cs.secondPoint.getX() + clonedOffset, cs.secondPoint.getY() + clonedOffset, 0),
                cs.filingColor);
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
        Color c = this.selected ? Color.BLUE : filingColor;
        Point3D fP = new Point3D(firstPoint.getX() - difX, firstPoint.getY() - difY, 0);
        Point3D sP = new Point3D(secondPoint.getX() - difX, secondPoint.getY() - difY, 0);
        drawingLine(gc, fP, sP, c);
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

        Color c = this.selected ? Color.BLUE : Color.BLACK;
        gc.setStroke(c);
        gc.strokeOval(firstPoint.getX() - difX, firstPoint.getY() - difY, higherX-lowerX, higherX-lowerX);
        gc.setStroke(Color.BLACK);
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
        else if (this.shape.equals("line")) {
            double m = (secondPoint.getY() - difY - (firstPoint.getY() - difY)) /
                    (secondPoint.getX() - difX - (firstPoint.getX() - difX));

            // y=mx+p
            // second.y=m*second.x+p
            // m*second.x+p-second.y=0
            // p=-m*second.x+second.y

            double p = -m * secondPoint.getX() - difX + secondPoint.getY() - difY;

            // Ax + By + c = 0
            // y=mx+p
            // A = m
            // b = -1
            // c = p
            //d(P,e)=∣∣∣Ax1+By1+CA2+B2
            double dist = (m * point.getX() - point.getY() + p) / (Math.sqrt(Math.pow(m, 2) + 1));

            if (dist <= 5 && dist >= -5) {
                this.selected = !this.selected;
            }
        }
        else if (shape.equals("ellipse")) {
            final double lowerX = Math.min(firstPoint.getX() - difX, secondPoint.getX() - difX);
            final double higherX = Math.max(firstPoint.getX() - difX, secondPoint.getX() - difX);

            final double radius = higherX-lowerX;

            Point3D upperLeft = new Point3D(firstPoint.getX() - difX, firstPoint.getY() - difY, 0);
            Point3D circleCenter = new Point3D(upperLeft.getX() + (radius/2), upperLeft.getY() + (radius/2), 0);

            boolean dist = Math.pow((point.getX()-circleCenter.getX()),2)/Math.pow(radius/2, 2) +
                    Math.pow((point.getY()-circleCenter.getY()),2)/Math.pow(radius/2,2) <= 1;

            if (dist) {
                System.out.println("selected");
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

    public void updateDif() {
        this.firstPoint = new Point3D(firstPoint.getX() - difX, firstPoint.getY() - difY, 0);
        this.secondPoint = new Point3D(secondPoint.getX() - difX, secondPoint.getY() - difY, 0);
        this.difY = 0;
        this.difX = 0;
        this.selected = false;
    }

    public void setFilingColor(GraphicsContext gc, Color c) {
        if (this.selected) {
            this.filingColor = c;
            drawingShape(gc);
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public int getId() {
        return id;
    }
}
