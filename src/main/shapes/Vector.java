package main.shapes;

public class Vector {

    double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public void setLength(double length) {
        double angle = getAngle();
        setX(length * Math.cos(angle));
        setY(length * Math.sin(angle));
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public void setAngle(double angle) {
        double length = getLength();
        setX(length * Math.cos(angle));
        setY(length * Math.sin(angle));
    }

    public Vector add(Vector v) {
        return new Vector(x + v.getX(), y + v.getY());
    }

    public void addTo(Vector v) {
        x += v.getX();
        y += v.getY();
    }

    public Vector subtract(Vector v) {
        return new Vector(x - v.getX(), y - v.getY());
    }

    public void subtractFrom(Vector v) {
        x -= v.getX();
        y -= v.getY();
    }

    public Vector multiply(double s) {
        return new Vector(x * s, y * s);
    }

    public void multiplyWith(double s) {
        x *= s;
        y *= s;
    }

    public double distance2(Vector v) {
        return (x - v.x) * (x - v.x) + (y - v.y) * (y - v.y);
    }

    public double dot(Vector v) {
        return x * v.x + y * v.y;
    }

    public Vector normalize() {
        double length = getLength();
        if (length != 0) {
            setX(x / length);
            setY(y / length);
        } else {
            setX(0);
            setY(0);
        }
        return this;
    }

}
