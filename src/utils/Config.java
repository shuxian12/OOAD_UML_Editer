package utils;

// record the configuration of the application

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

    public static class PORT_DIRECTION {
        public static final int NORTH = 0;
        public static final int EAST = 1;
        public static final int SOUTH = 2;
        public static final int WEST = 3;
    }
}
