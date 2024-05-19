package components.ui;

import components.module.Console;
import utils.Config.*;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// using oop to create a menu bar

public class MenuBar extends JMenuBar{
    private final Console console;

    public MenuBar(Console console){
        this.console = console;
        this.setBackground(Color.darkGray);

        for (String menu: MENU_CONFIG.MENU_LIST){
            if (menu.equals("Edit")){
                this.add(new Menu(menu, MENU_CONFIG.EDIT_ITEMS));
            } else {
                this.add(new Menu(menu, new HashMap<>()));
            }
        }
    }

    private class Menu extends JMenu{
        public Menu(String name, Map<String, Integer> items){
            super(name);
            for (String key: items.keySet()){
                this.add(new MenuItem(key, items.get(key)));
            }
        }
    }

    private class MenuItem extends JMenuItem{
        public MenuItem(String name, int menuItemId){
            super(name);
            this.addActionListener(e -> {
                onPressed(menuItemId);
            });
        }

        private void onPressed(int actionId){
            console.MenuPressed(actionId);
        }
    }
}
