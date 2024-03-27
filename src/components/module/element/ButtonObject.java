package components.module.element;

import components.module.mode.Mode;

import java.awt.*;
import java.util.ArrayList;
import static utils.Config.*;

public class ButtonObject {
    private ArrayList<BaseObject> buttons = new ArrayList<BaseObject>();

    public ButtonObject() {
        buttons.add(new ButtonElement(BUTTON_MODE.SELECT, BUTTON_TYPE.SELECT));
        buttons.add(new ButtonElement(BUTTON_MODE.ASSOCIATION, BUTTON_TYPE.LINE));
        buttons.add(new ButtonElement(BUTTON_MODE.GENERALIZATION, BUTTON_TYPE.LINE));
        buttons.add(new ButtonElement(BUTTON_MODE.COMPOSITION, BUTTON_TYPE.LINE));
        buttons.add(new ButtonElement(BUTTON_MODE.CCLASS, BUTTON_TYPE.SHAPE));
        buttons.add(new ButtonElement(BUTTON_MODE.USECASE, BUTTON_TYPE.SHAPE));
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
        public ButtonElement(int id, int type) {
            super(id, type);
        }

        @Override
        public void draw(Graphics g) {}
    }


}
