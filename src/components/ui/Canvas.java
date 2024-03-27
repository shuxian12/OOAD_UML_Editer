package components.ui;

import components.module.Console;

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
//        g.setColor(Color.white);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    private class PressListener extends MouseInputAdapter {
        public void mousePressed(MouseEvent e) {
            System.out.println("Pressed");
            console.CanvasPressed(e.getPoint());
//            presenter.onPressed(e.getPoint());
        }
    }

    private class DragListener extends MouseInputAdapter {
        public void mouseDragged(MouseEvent e) {
            System.out.println("Dragged");
//            presenter.onDragged(e.getPoint());
        }
    }
}
