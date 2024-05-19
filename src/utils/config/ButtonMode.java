package utils.config;

public enum ButtonMode {
    SELECT, ASSOCIATION, GENERALIZATION, COMPOSITION, CCLASS, USECASE;

    public static ButtonMode getMode(int mode) {
        return ButtonMode.values()[mode];
    }
}
