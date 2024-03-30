package components.ui;

import components.module.Console;
import components.module.element.UMLObject;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Canvas extends JPanel {
    private Console console;
    public Canvas(Console console) {
        this.console = console;
        this.setBackground(Color.gray);
        this.addMouseListener(new PressListener());
        this.addMouseMotionListener(new DragListener());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        console.getObjects().forEach(e -> {
            e.draw(g2d);
        });
//        g.setColor(Color.white);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    private class PressListener extends MouseInputAdapter {
        public void mousePressed(MouseEvent e) {
            System.out.println("Canvas Pressed");
            console.CanvasPressed(e.getPoint());
        }
    }

    private class DragListener extends MouseInputAdapter {
        public void mouseDragged(MouseEvent e) {
            System.out.println("Canvas Dragged");
        }
    }
}
