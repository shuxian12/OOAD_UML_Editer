package components.module.element.shape;

import components.module.element.BaseObject;
import utils.Config;
import utils.config.ObjectType;
import utils.config.PortDirection;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Port extends Shape{
    // Port is a small square shape that will be place in the use_cases or class to connect the lines,
    // and it will be visible when selected, and invisible when unselected.
    private final BaseObject parent;
    private final PortDirection direction;
    private final int PORT_SIZE = 10;
    private final Map<PortDirection, Point> locationMap = new HashMap<>();

    public Port(BaseObject parent, PortDirection direction){
        super(ObjectType.PORT.ordinal());
        this.name = "Port";
        this.height = PORT_SIZE;
        this.width = PORT_SIZE;
        this.parent = parent;
        this.direction = direction;

        this.updateMap();
        this.updateLocation();
    }

    private void updateMap() {
        Point parentSize = parent.getSize();
        locationMap.put(PortDirection.NORTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
                                                        parent.getLocation().y - height));
        locationMap.put(PortDirection.EAST, new Point(parent.getLocation().x + parentSize.x,
                                                        parent.getLocation().y + parentSize.y/2 - height/2));
        locationMap.put(PortDirection.SOUTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
                                                        parent.getLocation().y + parentSize.y));
        locationMap.put(PortDirection.WEST, new Point(parent.getLocation().x - width,
                                                        parent.getLocation().y + parentSize.y/2 - height/2));
    }

    @Override
    public void updateLocation(){
        this.updateMap();
        this.location = locationMap.get(direction);
//            System.out.println("Parent location: " + parent.getLocation() + " Port location: " + location);
    }

    @Override
    public Point getLocation() {
        if (direction == PortDirection.NORTH) {
            return new Point(location.x + width / 2, location.y + height);
        } else if (direction == PortDirection.WEST) {
            return new Point(location.x + width, location.y + height / 2);
        }
        return super.getLocation();
    }

    @Override
    public void draw(Graphics g) {
        updateLocation();

        if (parent.getSelected()) {
            g.setColor(Color.black);
            g.fillRect(location.x, location.y, width, height);
        }
    }

    public BaseObject getParent(){
        return parent;
    }
}
