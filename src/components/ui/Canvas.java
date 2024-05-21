package components.ui;

import components.module.Console;
import components.module.element.UMLObject;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Canvas extends JPanel {
    private Console console;
    public Canvas(Console console) {
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        this.console = console;
        this.setBackground(Color.white);
        this.setBorder(blackLine);
        this.addMouseListener(new MouseListener());
        this.addMouseMotionListener(new MouseListener());
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

    private class MouseListener extends MouseInputAdapter {
        public void mousePressed(MouseEvent e) {
//            System.out.println("Canvas Pressed");
            console.CanvasPressed(e.getPoint());
        }

        public void mouseDragged(MouseEvent e) {
//            System.out.println("Canvas Dragged");
            console.CanvasDragged(e.getPoint());
        }

        public void mouseReleased(MouseEvent e) {
//            System.out.println("Canvas Released");
            console.CanvasReleased(e.getPoint());
        }
    }
}
