package components.module.element;

import utils.config.ButtonMode;

import java.awt.*;
import java.util.ArrayList;

public class ButtonObject {
    private ArrayList<BaseObject> buttons = new ArrayList<BaseObject>();

    public ButtonObject() {
        for(ButtonMode mode : ButtonMode.values()) {
            buttons.add(new ButtonElement(mode.ordinal()));
        }
    }

    public void selectButton(int buttonId) {
        for (BaseObject button : buttons) {
            if (button.getId() == buttonId) {
                button.select();
            } else {
                button.unselect();
            }
        }

//        for (BaseObject button : buttons) {
//            System.out.println(button.getId() + " " + button.getSelected());
//        }
    }

    public boolean isSelected(int buttonId) {
        for (BaseObject button : buttons) {
            if (button.getId() == buttonId) {
                return button.getSelected();
            }
        }
        return false;
    }

    private class ButtonElement extends BaseObject {
        public ButtonElement(int id) {
            super(id);
        }

        @Override
        public void draw(Graphics g) {}
    }
}
