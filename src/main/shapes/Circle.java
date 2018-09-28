package main.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import main.Main;

public class Circle extends Shape {

    public static ArrayList<Circle> circles = new ArrayList<>();    // all circles
    public static Circle locked = null; // circle that is being moved by mouse

    private double radius, mass;
    
    private Vector prevoiusCenter = new Vector(0, 0);
    private double dx = 0, dy = 0;

    private Color color = Color.LIGHT_GRAY;

    public Circle(double x, double y, double radius, double mass) {
        this(new Vector(x, y), radius, mass);
    }

    public Circle(Vector center, double radius, double mass) {
        this.center = center;
        this.radius = radius;
        this.mass = mass;

        Circle.circles.add(this);
    }

    @Override
    public void update() {
        prevoiusCenter.setX(center.x);
        prevoiusCenter.setY(center.y);
        
        if (this.equals(locked)) {  // being moved by mouse
            center.setX(Main.mouseX - dx);
            center.setY(Main.mouseY - dy);
            
            bounce(Main.WIDTH, Main.HEIGHT);
            
            velocity.setX(center.x - prevoiusCenter.x);
            velocity.setY(center.y - prevoiusCenter.y);
        } else {    // moving freely
            velocity.addTo(acceleration);
            center.addTo(velocity);
            
            bounce(Main.WIDTH, Main.HEIGHT);
        }

        checkCollisions();
    }
    
    private void checkCollisions() {
        for (int i = Circle.circles.indexOf(this) + 1; i < Circle.circles.size(); i++) {
            Circle c = Circle.circles.get(i);
            if (collisionWithCircle(c)) {
                collide(c);
            }
        }
    }

    private void collide(Circle c) {
        Vector delta = center.subtract(c.center);
        double r = radius + c.radius;
        double dist2 = delta.dot(delta);

        if (dist2 > r * r) {    // no collision
            return;
        }

        double d = delta.getLength();

        Vector mtv; // minimum translation vector
        if (d != 0) {
            // delta je duzine d, a hocemo mtv koji ce biti duzine r-d
            mtv = delta.multiply((r - d) / d);
        } else {
            mtv = new Vector(r, 0);
        }

        double im1 = 1 / mass;
        double im2 = 1 / c.mass;

        // razmicemo ih da se dodiruju a ne prekklapaju
        center.addTo(mtv.multiply(im1 / (im1 + im2)));
        c.center.subtractFrom(mtv.multiply(im2 / (im1 + im2)));
        
        // podesavamo brzinu nakon sudara
        Vector v1 = velocity.multiply(1);
        Vector v2 = c.velocity.multiply(1);

        Vector x1 = center.multiply(1);
        Vector x2 = c.center.multiply(1);

        double m1 = mass;
        double m2 = c.mass;

        Vector x12 = x1.subtract(x2);
        Vector v12 = v1.subtract(v2);

        velocity = v1.subtract(x12.multiply(2 * m2 * v12.dot(x12) / (m1 + m2) / x12.dot(x12)));
        c.velocity = v2.add(x12.multiply(2 * m1 * v12.dot(x12) / (m1 + m2) / x12.dot(x12)));

        velocity.multiplyWith(bounce * c.bounce);
        c.velocity.multiplyWith(bounce * c.bounce);
    }

    public void bounce(int width, int height) {
        if (center.getX() - radius < 0) {
            center.setX(radius);
            velocity.setX(velocity.getX() * bounce);
            //checkCollisions();
        }
        if (center.getX() + radius > width) {
            center.setX(width - radius);
            velocity.setX(velocity.getX() * bounce);
            //checkCollisions();
        }
        if (center.getY() - radius < 0) {
            center.setY(radius);
            velocity.setY(velocity.getY() * bounce);
            //checkCollisions();
        }
        if (center.getY() + radius > height) {
            center.setY(height - radius);
            velocity.setY(velocity.getY() * bounce);
            //checkCollisions();
        }
    }

    public void accelerate(Vector a) {
        velocity.addTo(a);
    }

    @Override
    public void render(Graphics2D g) {
        Color old = g.getColor();
        g.setColor(color);
        g.fillOval((int) (center.getX() - radius), (int) (center.getY() - radius), (int) (2 * radius), (int) (2 * radius));
        g.setColor(Color.black);
        g.drawOval((int) (center.getX() - radius), (int) (center.getY() - radius), (int) (2 * radius), (int) (2 * radius));
        g.setColor(old);
    }

    @Override
    public boolean pointInside(double x, double y) {
        double dx = x - center.getX();
        double dy = y - center.getY();
        return (dx * dx + dy * dy) <= (radius * radius);
    }

    public boolean collisionWithCircle(Circle c) {
        return center.distance2(c.center) <= (radius + c.radius) * (radius + c.radius);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

}
