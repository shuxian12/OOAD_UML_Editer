package components.module;

import components.module.controller.CanvasController;
import components.module.controller.MenuController;
import components.module.controller.ToolBarController;
import utils.IDraw;
import utils.IObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Console {
    private ToolBarController toolBarController = new ToolBarController();
    private CanvasController canvasController = new CanvasController();
    private MenuController menuController = new MenuController();
    private IObserver observer;
    public Console() {
        System.out.println("Console initialized");
    }

    public void setObserver(IObserver observer) {
        this.observer = observer;
    }

    public void ToolBarPressed(int buttonId) {
        toolBarController.onButtonPressed(buttonId);
        canvasController.setCanvasMode(buttonId);
        notifyObserver();
    }

    public boolean isSelected(int buttonId) {
        return toolBarController.isSelected(buttonId);
    }

    public void notifyObserver() {
        this.observer.update();
    }

    public void CanvasPressed(Point point) {
        canvasController.onPressed(point);
        notifyObserver();
    }

    public void CanvasDragged(Point point) {
        canvasController.onDragged(point);
        notifyObserver();
    }

    public void CanvasReleased(Point point) {
        canvasController.onReleased(point);
        notifyObserver();
    }

    public void MenuPressed(int actionId) {
//        menuController.onMenuPressed(actionId);
        canvasController.doAction(actionId);

        if (canvasController.doRenameValid()) {
            changeObjectName(JOptionPane.showInputDialog(
                    new JFrame(),
                    "Enter the new object name"));
        }
        notifyObserver();
    }

    public ArrayList<IDraw> getObjects() {
        return canvasController.getUMLObject();
    }

    private void changeObjectName(String name) {
        if (name == null)
            return;
        canvasController.changeObjectName(name);
    }
}
