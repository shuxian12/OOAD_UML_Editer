package components.module.element.line;

import components.module.element.BaseObject;
import components.module.element.shape.Port;

import java.awt.*;

public abstract class Line extends BaseObject {
    protected BaseObject startPort;
    protected BaseObject endPort;
    protected Point startLocation;
    protected Point endLocation;
    protected final int ARROW_SIZE = 10;
    protected int dx, dy;
    Line(int id){
        super(id);
    }
    public void setConnection(Port start, Port end){
        this.startPort = start;
        this.endPort = end;
    }

    public void setLocation(Point start, Point end){
        this.startLocation = start;
        this.endLocation = end;
    }
    @Override
    public void draw(Graphics g) {
        if (this.endPort != null)
            this.setLocation(this.startPort.getLocation(), this.endPort.getLocation());
        double angle = Math.atan2(endLocation.y - startLocation.y, endLocation.x - startLocation.x);
        dx = (int) (ARROW_SIZE * Math.cos(angle));
        dy = (int) (ARROW_SIZE * Math.sin(angle));

        g.setColor(Color.black);
        g.drawLine(startLocation.x, startLocation.y, endLocation.x, endLocation.y);
    }
}
