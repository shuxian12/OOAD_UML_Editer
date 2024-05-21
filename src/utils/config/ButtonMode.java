package utils.config;

import components.module.controller.CanvasController;
import components.module.mode.LineMode;
import components.module.mode.Mode;
import components.module.mode.ObjectMode;
import components.module.mode.SelectMode;

public enum ButtonMode {
    SELECT, ASSOCIATION, GENERALIZATION, COMPOSITION, CCLASS, USECASE;

    public static ButtonMode getMode(int mode) {
        return ButtonMode.values()[mode];
    }

    public Mode getCanvasMode(CanvasController canvasController, int mode) {
        switch (this) {
            case SELECT:
                return new SelectMode(canvasController, mode);
            case ASSOCIATION:
            case GENERALIZATION:
            case COMPOSITION:
                return new LineMode(canvasController, mode);
            case CCLASS:
            case USECASE:
                return new ObjectMode(canvasController, mode);
            default:
                return null;
        }
    }
}
