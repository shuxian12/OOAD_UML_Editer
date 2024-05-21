package components.module.element.shape;

import components.module.element.BaseObject;
import utils.config.PortDirection;

import java.awt.*;
import java.util.ArrayList;

public abstract class Shape extends BaseObject {
    protected String name = "";
    protected ArrayList<BaseObject> ports = new ArrayList<>();
    Shape(int id){
        super(id);
    }
    public void setName(String name){
        this.name = name;
    }
    protected void addPort(){
        ports.add(new Port(this, PortDirection.NORTH));
        ports.add(new Port(this, PortDirection.EAST));
        ports.add(new Port(this, PortDirection.SOUTH));
        ports.add(new Port(this, PortDirection.WEST));
    }
    public BaseObject findPort(Point point){
        int centerX = location.x + width / 2;
        int centerY = location.y + height / 2;
        Point normPoint = new Point(point.x - centerX, point.y - centerY);

        if (normPoint.x + normPoint.y > 0) {
            if (normPoint.x - normPoint.y > 0) {
                return ports.get(PortDirection.EAST.ordinal());
            }
            return ports.get(PortDirection.SOUTH.ordinal());
        } else {
            if (normPoint.x - normPoint.y > 0) {
                return ports.get(PortDirection.NORTH.ordinal());
            }
            return ports.get(PortDirection.WEST.ordinal());
        }
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        for (BaseObject port : ports) {
            port.updateLocation();
            if (!this.isGroup){
                port.draw(g);
            }
        }

        g.setColor(Color.lightGray);
//            g.fillRect(location.x, location.y, width, height);
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
//            System.out.println("Shape move from " + location + " to " + new Point(location.x + dx, location.y + dy));
    }
}
