package components.module.controller;

import components.module.element.UMLObject;
import components.module.mode.LineMode;
import components.module.mode.Mode;
import components.module.mode.ObjectMode;
import components.module.mode.SelectMode;
import utils.Config.*;
import utils.IDraw;

import java.awt.*;
import java.util.ArrayList;

public class CanvasController {
    private UMLObject umlObject = new UMLObject();
    private Mode mode = new SelectMode(this);
    private boolean isRename = false;

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

    public boolean doRenameValid() {
        if (isRename) {
            isRename = false;
            return umlObject.checkRenameValid();
        }
        return false;
    }

    public void changeObjectName(String name) {
        umlObject.rename(name);
    }

    public int getCanvasMode() {
        return this.mode.getMode();
    }

    public void onPressed(Point point) {
        System.out.println("Canvas Pressed");
        umlObject.unselectALL();
        this.mode.onPressed(point);
    }

    public void onDragged(Point point) {
//        System.out.println("Canvas Dragged");
        this.mode.onDragged(point);
    }

    public void onReleased(Point point) {
        System.out.println("Canvas Released");
        this.mode.onReleased(point);
    }

    public UMLObject.Base findObject(Point point) {
        System.out.println("Finding object");
        umlObject.unselectALL();
        return umlObject.findObject(point);
    }

    public UMLObject.Port findPort(Point point) {
        System.out.println("Finding port");
        return umlObject.findPort(point);
    }

    public void setAreaObjects(Point topCorner, Point bottomCorner) {
        System.out.println("Getting area objects");
        umlObject.setAreaObjects(topCorner, bottomCorner);
    }

    public UMLObject.Base createObject(Point point) {
        System.out.println("Creating object");
        return umlObject.addObject(this.getCanvasMode(), point);
    }

    public UMLObject.Line createLine() {
        System.out.println("Creating line");
        return umlObject.addLine(this.getCanvasMode());
    }

    public void removeObject(UMLObject.Base object) {
        System.out.println("Removing object");
        umlObject.removeObject(object);
    }

    public ArrayList<IDraw> getUMLObject() {
        return umlObject.getDrawMethod();
    }

    public void unselectALL() {
        umlObject.unselectALL();
    }

    public void doAction(int action, UMLObject.Base object) {
        System.out.println("Doing action");
//        umlObject.doAction(action, object);
        switch (action) {
            case MENU_CONFIG.GROUP:
                umlObject.group();
                break;
            case MENU_CONFIG.UNGROUP:
                umlObject.ungroup();
                break;
            case MENU_CONFIG.RENAME:
                isRename = true;
                break;
            default:
                System.out.println("Warning: Get Unsupported MenuItemId at Presenter.onPressed()");
                break;
        }
    }
}

