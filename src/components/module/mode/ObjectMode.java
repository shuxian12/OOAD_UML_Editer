package components.module.mode;

import components.module.controller.CanvasController;
import static utils.Config.*;
import java.awt.*;

public class ObjectMode extends Mode{
    public ObjectMode(CanvasController canvasController, int mode) {
        super(canvasController, mode);
        System.out.println("ObjectMode initialized");
    }

    @Override
    public void onPressed(Point point) {
        System.out.println("ObjectMode onPressed");
        this.canvasController.createObject(point);
    }

    @Override
    public void onDragged(Point point) {
        System.out.println("ObjectMode onDragged");
    }

    @Override
    public void onReleased(Point point) {
        System.out.println("ObjectMode onReleased");
    }
}
