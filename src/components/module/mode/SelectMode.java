package components.module.mode;

import components.module.controller.CanvasController;

import java.awt.*;

public class SelectMode extends Mode{
    public SelectMode(CanvasController canvasController) {
        super(canvasController);
        System.out.println("SelectMode initialized");
    }

    @Override
    public void onPressed(Point point) {
        System.out.println("SelectMode onPressed");
        this.canvasController.findObject(point);
    }
}
