package utils.config;

public enum ObjectType {
    SELECT, ASSOCIATION, GENERALIZATION, COMPOSITION, CCLASS, USECASE, PORT, GROUP, SELECT_SQUARE;

    public static ObjectType getMode(int mode) {
        return ObjectType.values()[mode];
    }
}
