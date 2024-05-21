package components.module.mode;

import components.module.controller.CanvasController;

import java.awt.*;

public abstract class Mode {
    private int mode = 0;
    protected CanvasController canvasController;

    public Mode(CanvasController canvasController, int mode) {
        System.out.println("Mode initialized");
        this.canvasController = canvasController;
        this.mode = mode;
    }

    public int getMode() {
        return this.mode;
    }

    public abstract void onPressed(Point point);

    public abstract void onDragged(Point point);

    public abstract void onReleased(Point point);
}
