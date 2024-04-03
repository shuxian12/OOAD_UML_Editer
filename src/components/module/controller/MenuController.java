package components.module.controller;

import components.module.element.MenuObject;

public class MenuController {
    private static final MenuObject MENU_BAR = new MenuObject();

    public MenuController() {
        System.out.println("MenuController initialized");
    }

    public void onMenuPressed(int menuId) {
//        MENU_BAR.action(menuId);

    }
}
