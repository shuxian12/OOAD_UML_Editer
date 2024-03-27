package components.module.mode;

import components.module.controller.CanvasController;

import java.awt.*;

public class LineMode extends Mode{
    public LineMode(CanvasController canvasController) {
        super(canvasController);
        System.out.println("LineMode initialized");
    }

    @Override
    public void onPressed(Point point) {
        System.out.println("LineMode onPressed");
        if(this.canvasController.getCanvasMode() == 1) {
            System.out.println("Creating line");
        } else if (this.canvasController.getCanvasMode() == 2) {
            System.out.println("Selecting line");
        }
    }
}
