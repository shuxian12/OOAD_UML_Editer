package components.module;

import components.module.controller.CanvasController;
import components.module.controller.ToolBarController;
import utils.IObserver;

import java.awt.*;

public class Console {
    private ToolBarController toolBarController = new ToolBarController();
    private CanvasController canvasController = new CanvasController();
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
}
