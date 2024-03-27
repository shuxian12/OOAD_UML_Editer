package components.module.mode;

import components.module.controller.CanvasController;
import static utils.Config.*;
import java.awt.*;

public class ObjectMode extends Mode{
    public ObjectMode(CanvasController canvasController) {
        super(canvasController);
        System.out.println("ObjectMode initialized");
    }

    @Override
    public void onPressed(Point point) {
        System.out.println("ObjectMode onPressed");
        this.canvasController.createObject(point);
    }
}
