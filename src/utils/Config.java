package utils;

// record the configuration of the application

import java.awt.*;
import java.util.Map;

public class Config {
    public enum BUTTON_MODE {
        SELECT, ASSOCIATION, GENERALIZATION, COMPOSITION, CCLASS, USECASE;

        public static BUTTON_MODE getMode(int mode) {
            return BUTTON_MODE.values()[mode];
        }
    }

    public static class BUTTON_TYPE {
        public static final int SELECT = 0;
        public static final int LINE = 1;
        public static final int SHAPE = 2;
    }

    public static class OBJECT_TYPE {
        public static final int PORT = 6;
        public static final int GROUP = 7;
        public static final int SELECT_SQUARE = 8;
    }

    public enum PORT_DIRECTION {
        NORTH, EAST, SOUTH, WEST
    }

    public static class MENU_CONFIG {
        public static final String[] MENU_LIST = {"File", "Edit"};

        public static final int GROUP = 0;
        public static final int UNGROUP = 1;
        public static final int RENAME = 2;

        public static final Map<String, Integer> EDIT_ITEMS = Map.of(
            "Group", GROUP,
            "Ungroup", UNGROUP,
            "Rename", RENAME
        );
    }
}
