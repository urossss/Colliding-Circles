package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import main.display.Display;
import main.shapes.Circle;
import main.shapes.Vector;

public class Main implements MouseListener, MouseMotionListener, Runnable {

    private boolean running = false;
    private Thread thread;

    private Display display;

    private final String title;
    private final int width, height;

    public static int WIDTH, HEIGHT;

    private Graphics2D g;
    private BufferStrategy bs;

    public static int mouseX = 0, mouseY = 0;

    private Vector gravity = new Vector(0, 0.1);

    public Main(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        WIDTH = width;
        HEIGHT = height;
    }

    private void init() {
        display = new Display(title, width, height);
        display.getCanvas().addMouseListener(this);
        display.getCanvas().addMouseMotionListener(this);

        Circle c = null;
        for (int i = 0; i < 60; i++) {
            c = new Circle(Math.random() * width, Math.random() * height, Math.random() * 10 + 10, Math.random() * 5 + 2);
            c.getVelocity().setX((Math.random() - 0.5) * 20);
            c.getVelocity().setY((Math.random() - 0.5) * 20);
            c.setColor(Color.getHSBColor((float) c.getMass() / 7, 0.9f, 0.9f));
        }
        if (c != null) {
            c.setRadius(50);
        }
    }

    private void render(Graphics2D g) {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = (Graphics2D) bs.getDrawGraphics();

        g.clearRect(0, 0, width, height);

        for (Circle c : Circle.circles) {
            c.accelerate(gravity);
            c.update();
        }

        for (Circle c : Circle.circles) {
            c.render(g);
        }
        
        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();

        int fps = 60;
        double timePerFrame = 1000000000.0 / fps;
        long last, now = System.nanoTime(), delta = 0;

        while (running) {
            last = now;
            now = System.nanoTime();
            delta += now - last;

            if (delta >= timePerFrame) {
                delta -= timePerFrame;
                display.getFrame().requestFocusInWindow();
                render(g);
            }
        }

        stop();
    }

    public static void main(String args[]) {
        new Main("Collision", 1400, 800).start();
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();

        for (Circle c : Circle.circles) {
            if (c.pointInside(me.getX(), me.getY())) {
                c.setDx(mouseX - c.getCenter().getX());
                c.setDy(mouseY - c.getCenter().getY());
                Circle.locked = c;
                return;
            }
        }
        Circle.locked = null;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();

        Circle.locked = null;
    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    @Override
    public void mouseDragged(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
    }

    @Override
    public void mouseMoved(MouseEvent me) {

    }

    // Thread manipulation
    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
    }

}
