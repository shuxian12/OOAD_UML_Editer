package utils;

// record the configuration of the application

import java.awt.*;
import java.util.Map;

public class Config {
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
