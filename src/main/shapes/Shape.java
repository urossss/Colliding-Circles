package main.shapes;

import java.awt.Graphics2D;

public abstract class Shape {
    
    protected Vector center;
    protected double bounce = -0.95;
    protected Vector velocity = new Vector(0, 0), acceleration = new Vector(0, 0);
    
    public abstract void update();

    public abstract void render(Graphics2D g);
    
    public abstract boolean pointInside(double x, double y);

    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
        this.center = center;
    }

    public double getBounce() {
        return bounce;
    }

    public void setBounce(double bounce) {
        this.bounce = bounce;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }
    
}
