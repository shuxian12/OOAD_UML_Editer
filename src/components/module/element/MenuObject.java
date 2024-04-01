package components.module.element;

import java.util.ArrayList;
import utils.Config.*;

public class MenuObject {
    private ArrayList<Menu> menus = new ArrayList<>();

    private int mode = 0;
    public MenuObject() {
        System.out.println("MenuObject initialized");

        menus.add(new File());
        menus.add(new Edit());
    }

//    public void action(int mode) {
//        this.mode = mode;
//
//        switch (mode) {
//            case MENU_CONFIG.GROUP:
//
//        }
//    }
//
//    private void group() {
//        System.out.println("Grouping");
//
//    }

    public int getMode() {
        return this.mode;
    }

    private class Menu {
        private ArrayList<MenuItem> menuItems = new ArrayList<>();
        private String name;

        public Menu(String name) {
            this.name = name;
        }

        public void addMenuItem(MenuItem menuItem) {
            menuItems.add(menuItem);
        }

        public ArrayList<MenuItem> getMenuItems() {
            return menuItems;
        }
    }

    private class File extends Menu {
        public File() {
            super("File");
        }
    }

    private class Edit extends Menu {
        public Edit() {
            super("Edit");
            super.addMenuItem(new Group());
            super.addMenuItem(new Ungroup());
            super.addMenuItem(new Rename());

        }
    }

    private class MenuItem {
        private String name;

        public MenuItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private class Group extends MenuItem {
        public Group() {
            super("Group");
        }
    }

    private class Ungroup extends MenuItem {
        public Ungroup() {
            super("Ungroup");
        }
    }

    private class Rename extends MenuItem {
        public Rename() {
            super("Rename");
        }
    }
}
