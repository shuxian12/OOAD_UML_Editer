package components.ui;

import javax.swing.*;
import java.awt.*;

// using oop to create a toolbar on the left side of the main frame

public class ToolBar extends JPanel {
    private final static String[] TOOL_LIST = {"select", "association", "generalization",
            "composition", "class", "use_case"};
    private final static String TOOL_PATH = "./img/";
    // tool_img_path = "./img/" + tool + ".png"

    private final static int WIDTH = 90;
    private final static int HEIGHT = 600;
    private final static int BTN_SIZE = 80;

    public ToolBar() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.white);
        for (String tool : TOOL_LIST) {
            this.add(new ToolButton(tool));
        }
    }

    private String getToolPath(String tool) {
        return TOOL_PATH + tool + ".png";
    }

    private class ToolButton extends JButton {
        public final String btn_id;
        public boolean is_selected = false;

        public ToolButton(String name) {
            super();
            this.btn_id = name;
            this.setPreferredSize(new Dimension(BTN_SIZE, BTN_SIZE));
            this.addActionListener(e ->
                onPressed(this)
            );
//            this.setBackground(null);
            this.setOpaque(true);
        }

//        @Override
        protected void paintComponent(Graphics g) {
            // 等比例縮小圖片
//            System.out.println("paintComponent " + this.is_selected);
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            if (this.is_selected) {
                g2d.setColor(new Color(237, 237, 237));
            } else {
                g2d.setColor(Color.white);
            }
            g2d.fillRect(0, 0, BTN_SIZE, BTN_SIZE);
            g2d.setColor(Color.black);
            g2d.drawRect(0, 0, BTN_SIZE-1, BTN_SIZE-1);
            g2d.drawImage(new ImageIcon(getToolPath(btn_id)).getImage(), 5, 5,
                    BTN_SIZE - 10, BTN_SIZE - 10, null);
//            super.paintComponent(g);
        }

        private void onPressed(ToolButton button) {
            System.out.println("Button " + button.btn_id + " is pressed");
            button.is_selected = !button.is_selected;
        }
    }
}
