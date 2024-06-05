package components.module.mode;

import components.module.controller.CanvasController;
import components.module.element.line.Line;
import components.module.element.shape.Port;

import java.awt.*;

public class LineMode extends Mode{
    private Line line;
    private Port startPort;
    private Port endPort;
    private boolean validPress = false;
    public LineMode(CanvasController canvasController, int mode) {
        super(canvasController, mode);
        System.out.println("LineMode initialized");
    }

    @Override
    public void onPressed(Point point) {
        this.startPort = this.canvasController.findPort(point);
        System.out.println("LineMode onPressed startPort: " + this.startPort);
        validPress = false;
        if (this.startPort != null) {
            validPress = true;
            System.out.println("Line created");
            this.line = this.canvasController.createLine();
            this.line.setConnection(this.startPort, null);
            this.line.setLocation(this.startPort.getLocation(), point);
        }
    }

    @Override
    public void onDragged(Point point) {
        if (!validPress) {
            return;
        }
//        System.out.println("LineMode onDragged");
        this.line.setLocation(this.startPort.getLocation(), point);
    }

    @Override
    public void onReleased(Point point) {
        if (!validPress) {
            return;
        }
        System.out.println("LineMode onReleased");
        this.endPort = this.canvasController.findPort(point);
        if (this.endPort != null &&
                this.startPort.getParent() != this.endPort.getParent()){
            this.line.setConnection(this.startPort, this.endPort);
        } else {
            this.canvasController.removeObject(this.line);
        }
    }
}
