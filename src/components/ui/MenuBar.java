package components.ui;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.*;

// using oop to create a menu bar

public class MenuBar extends JMenuBar{
    private final static String[] MENU_List = {"File", "Edit"};
    private final static String[] EDIT_ITEMS = {"Group", "Ungroup", "Rename"};

    public MenuBar(){
        this.setBackground(Color.darkGray);

        for (String menu: MENU_List){
            if (menu.equals("Edit")){
                this.add(new Menu(menu, EDIT_ITEMS));
            } else {
                this.add(new Menu(menu, new String[]{}));
            }
        }
    }

    private class Menu extends JMenu{
        public Menu(String name, String[] items){
            super(name);
            for (String item: items){
                this.add(new MenuItem(item));
            }
        }
    }

    private class MenuItem extends JMenuItem{
        public MenuItem(String name){
            super(name);
        }
    }
}
