package components.ui;

import components.module.Console;
import utils.IObserver;

import javax.swing.JFrame;

public class MainFrame extends JFrame implements IObserver {
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    private final static String title = "UML Editor";
    private Console console;
    public MainFrame(Console console){
        this.console = console;
        console.setObserver(this);

        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle(title);
        this.add(new ToolBar(this.console), "West");
        this.add(new Canvas(this.console), "Center");
        this.add(new MenuBar(), "North");

        this.setVisible(true);
    }

    @Override
    public void update() {
        repaint();
    }
}
