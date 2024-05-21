package components.module.controller;

import components.module.element.BaseObject;
import components.module.element.UMLObject;
import components.module.element.line.Line;
import components.module.element.shape.Port;
import components.module.mode.LineMode;
import components.module.mode.Mode;
import components.module.mode.ObjectMode;
import components.module.mode.SelectMode;
import utils.Config.*;
import utils.IDraw;
import utils.config.ButtonMode;

import java.awt.*;
import java.util.ArrayList;

public class CanvasController {
    private UMLObject umlObject = new UMLObject();
    private Mode mode = new SelectMode(this);
    private Mode lineMode = new LineMode(this);
    private Mode selectMode = new SelectMode(this);
    private Mode objectMode = new ObjectMode(this);
    private boolean isRename = false;

    public CanvasController() {
        System.out.println("CanvasController initialized");
    }

    public void setCanvasMode(int mode) {
        ButtonMode buttonMode = ButtonMode.getMode(mode);
        if (buttonMode == ButtonMode.SELECT){
            this.mode = this.selectMode;
        } else if (buttonMode == ButtonMode.ASSOCIATION ||
                   buttonMode == ButtonMode.COMPOSITION ||
                   buttonMode == ButtonMode.GENERALIZATION) {
            this.mode = this.lineMode;
        } else if (buttonMode == ButtonMode.CCLASS ||
                   buttonMode == ButtonMode.USECASE) {
            this.mode = this.objectMode;
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
        System.out.println("Canvas Dragged");
        this.mode.onDragged(point);
    }

    public void onReleased(Point point) {
        System.out.println("Canvas Released");
        this.mode.onReleased(point);
    }

    public BaseObject findObject(Point point) {
        System.out.println("Finding object");
        umlObject.unselectALL();
        return umlObject.findObject(point);
    }

    public Port findPort(Point point) {
        System.out.println("Finding port");
        return umlObject.findPort(point);
    }

    public void setAreaObjects(Point topCorner, Point bottomCorner) {
        System.out.println("Getting area objects");
        umlObject.setAreaObjects(topCorner, bottomCorner);
    }

    public BaseObject createObject(Point point) {
        System.out.println("Creating object");
        return umlObject.addObject(this.getCanvasMode(), point);
    }

    public Line createLine() {
        System.out.println("Creating line");
        return umlObject.addLine(this.getCanvasMode());
    }

    public void removeObject(BaseObject object) {
        System.out.println("Removing object");
        umlObject.removeObject(object);
    }

    public ArrayList<IDraw> getUMLObject() {
        return umlObject.getDrawMethod();
    }

    public void unselectALL() {
        umlObject.unselectALL();
    }

    public void doAction(int action) {
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

