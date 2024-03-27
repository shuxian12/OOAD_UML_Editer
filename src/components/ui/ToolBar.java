package components.ui;

import components.module.Console;
import components.module.controller.ToolBarController;

import javax.swing.*;
import java.awt.*;

import static utils.Config.*;

// using oop to create a toolbar on the left side of the main frame

public class ToolBar extends JPanel {
    private final static String[] TOOL_LIST = {
            "select", "association", "generalization",
            "composition", "class", "use_case"
    };
    private final static int[] TOOL_NUM_LIST = {
            BUTTON_MODE.SELECT,
            BUTTON_MODE.ASSOCIATION,
            BUTTON_MODE.GENERALIZATION,
            BUTTON_MODE.COMPOSITION,
            BUTTON_MODE.CCLASS,
            BUTTON_MODE.USECASE
    };
    private final static String TOOL_PATH = "./img/";

    private final static int WIDTH = 90;
    private final static int HEIGHT = 600;
    private final static int BTN_SIZE = 80;

    private final Console console;

    public ToolBar(Console console) {
        this.console = console;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.white);
        for (String tool : TOOL_LIST) {
            this.add(new ToolButton(tool));
        }
    }

    private String getToolPath(String tool) {
        return TOOL_PATH + tool + ".png";
    }

    private int getBtnId(String tool) {
        for (int i = 0; i < TOOL_LIST.length; i++) {
            if (TOOL_LIST[i].equals(tool)) {
                return TOOL_NUM_LIST[i];
            }
        }
        return -1;
    }

    private class ToolButton extends JButton {
        public final String btn_id;
        public final int id;
//        public boolean is_selected = false;

        public ToolButton(String name) {
            super();
            this.btn_id = name;
            this.id = getBtnId(name);
            this.setPreferredSize(new Dimension(BTN_SIZE, BTN_SIZE));
            this.addActionListener(e ->
                    onPressed(this)
            );
            // this.setBackground(null);
            this.setOpaque(true);
        }

        //  @Override
        protected void paintComponent(Graphics g) {
            // 等比例縮小圖片
            // System.out.println("paintComponent " + this.btn_id + " " + console.isSelected(this.id));
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            if (console.isSelected(this.id)) {
                g2d.setColor(new Color(237, 237, 237));
            } else {
                g2d.setColor(Color.white);
            }
            g2d.fillRect(0, 0, BTN_SIZE, BTN_SIZE);
            g2d.setColor(Color.black);
            g2d.drawRect(0, 0, BTN_SIZE - 1, BTN_SIZE - 1);
            g2d.drawImage(new ImageIcon(getToolPath(btn_id)).getImage(), 5, 5,
                    BTN_SIZE - 10, BTN_SIZE - 10, null);
            // super.paintComponent(g);
        }

        private void onPressed(ToolButton button) {
            // System.out.println("Button " + button.btn_id + " is pressed\n");
            console.ToolBarPressed(button.id);
        }
    }
}
