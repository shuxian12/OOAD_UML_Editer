package components.module.element.shape;

import components.module.element.BaseObject;
import utils.config.ObjectType;

import java.awt.*;
import java.util.ArrayList;

public class Group extends Shape {
    private ArrayList<BaseObject> groupObjects;
    private Point startLocation;
    private Point endLocation;
    public Group(Point startLocation, Point endLocation, ArrayList<BaseObject> groupObjects){
        super(ObjectType.GROUP.ordinal());
        super.setName("Group");
        this.setLocation(startLocation, endLocation);
        this.groupObjects = groupObjects;
        this.setGroupType();
    }

    public void setLocation(Point start, Point end){
        this.startLocation = start;
        this.endLocation = end;
    }

    private void setGroupType(){
        for (BaseObject object : groupObjects) {
            object.setGroup(true);
        }
    }

    @Override
    public Point getLocation() {
        return startLocation;
    }

    public Point getEndLocation() {
        return endLocation;
    }

    public ArrayList<BaseObject> getInnerObjects() {
        return groupObjects;
    }

    @Override
    public boolean canUngroup() {
        return true;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        for (BaseObject object : groupObjects) {
            object.draw(g);
        }

        if (getSelected() && !isGroup) {
            g.setColor(Color.black);
        }
        else {
            g.setColor(Color.lightGray);
        }
        g.drawRect(startLocation.x, startLocation.y,
                endLocation.x - startLocation.x, endLocation.y - startLocation.y);
    }

    @Override
    public void move(int dx, int dy) {
//            System.out.println("Group move from " + startLocation + "," + endLocation + " to " +
//                    new Point(startLocation.x + dx, startLocation.y + dy) + ", " + new Point(endLocation.x + dx, endLocation.y + dy));
        this.startLocation.x += dx;
        this.startLocation.y += dy;
        this.endLocation.x += dx;
        this.endLocation.y += dy;
        for (BaseObject object : groupObjects) {
            object.move(dx, dy);
        }
    }

    @Override
    public boolean contains(Point pt) {
        for(BaseObject object: groupObjects) {
            if (object.contains(pt)) {
                return true;
            }
        }
        return false;
//            return pt.x > startLocation.x && pt.x < endLocation.x &&
//                    pt.y > startLocation.y && pt.y < endLocation.y;

    }

    @Override
    public boolean findContainObject(Point pt) {
        return false;
    }

    @Override
    public boolean containInArea(Point start, Point end) {
        return startLocation.x > start.x && endLocation.x < end.x &&
                startLocation.y > start.y && endLocation.y < end.y;
    }

    @Override
    public Point getBottomCorner() {
        return endLocation;
    }
}

