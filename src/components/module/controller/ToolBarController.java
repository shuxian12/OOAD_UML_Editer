package components.module.controller;

import components.module.element.ButtonObject;

public class ToolBarController {
    private ButtonObject buttonObject = new ButtonObject();
    public ToolBarController() {
        System.out.println("ToolBarController initialized");
    }

    public void onButtonPressed(int buttonId) {
        buttonObject.selectButton(buttonId);
    }

    public boolean isSelected(int buttonId) {
        return buttonObject.isSelected(buttonId);
    }

//    private class ButtonObject extends BaseObject {
//        public ButtonObject() {
//            System.out.println("ButtonObject initialized");
//        }
//    }


}
