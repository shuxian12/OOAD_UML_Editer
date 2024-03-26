package components.ui;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    private final static String title = "UML Editor";
    public MainFrame(){
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle(title);
        this.add(new ToolBar(), "West");
        this.add(new MenuBar(), "North");

        this.setVisible(true);
    }
}
