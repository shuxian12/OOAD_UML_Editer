package utils;

// record the configuration of the application

import java.awt.*;
import java.util.Map;

public class Config {
    public static class BUTTON_MODE {
        public static final int SELECT = 0;
        public static final int ASSOCIATION = 1;
        public static final int GENERALIZATION = 2;
        public static final int COMPOSITION = 3;
        public static final int CCLASS = 4;
        public static final int USECASE = 5;

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

    public static class PORT_DIRECTION {
        public static final int NORTH = 0;
        public static final int EAST = 1;
        public static final int SOUTH = 2;
        public static final int WEST = 3;
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
