package main.display;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Display {
    
    private JFrame frame;
    private Canvas canvas;
    
    private String title;
    private int width, height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        
        createDisplay();
    }
    
    private void createDisplay() {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height + 30);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        canvas = new Canvas();
        canvas.setSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        
        frame.add(canvas);
        
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }

}
