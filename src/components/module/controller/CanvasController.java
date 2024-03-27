package components.module.controller;

import components.module.element.ButtonObject;
import components.module.element.UMLObject;
import components.module.mode.LineMode;
import components.module.mode.Mode;
import components.module.mode.ObjectMode;
import components.module.mode.SelectMode;
import utils.Config.*;

import java.awt.*;

public class CanvasController {
//    private int CANVAS_MODE = 0;

    private UMLObject umlObject = new UMLObject();
    private Mode mode;

    public CanvasController() {
        System.out.println("CanvasController initialized");
    }

    public void setCanvasMode(int mode) {
        if (mode == BUTTON_MODE.SELECT){
            this.mode = new SelectMode(this);
        } else if (mode == BUTTON_MODE.ASSOCIATION ||
                   mode == BUTTON_MODE.COMPOSITION ||
                   mode == BUTTON_MODE.GENERALIZATION) {
            this.mode = new LineMode(this);
        } else if (mode == BUTTON_MODE.CCLASS ||
                   mode == BUTTON_MODE.USECASE) {
            this.mode = new ObjectMode(this);
        }
        this.mode.setMode(mode);
    }

    public int getCanvasMode() {
        return this.mode.getMode();
    }

    public void onPressed(Point point) {
        System.out.println("Canvas Pressed");
        this.mode.onPressed(point);
    }

    public void findObject(Point point) {
        System.out.println("Finding object");

    }

    public void createObject(Point point) {
        System.out.println("Creating object");
        umlObject.addObject(this.getCanvasMode(), point);
    }
}

