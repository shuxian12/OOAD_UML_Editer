package components.module.element;

import utils.IDraw;

import java.awt.*;
import java.util.ArrayList;

public abstract class BaseObject{
    protected Point location = new Point();
    protected int width;
    protected int height;
    protected int id;
//    protected int type;
    protected int depth;
    protected boolean selected = false;
    protected boolean isGroup = false;

    public BaseObject(){} // Default constructor

    public BaseObject(int id){
        this.id = id;
//        this.type = type;
    }

    abstract public void draw(Graphics g);

    public int getId() { return id; }
    public Point getSize() { return new Point(width, height); }
    public Point getLocation() { return location; }
    public IDraw getDrawMethod() {
        return (Graphics2D g) -> draw(g);
    }
    public boolean getSelected() { return selected; }
    public Point getBottomCorner() { return new Point(location.x + width, location.y + height); }
    public ArrayList<BaseObject> getInnerObjects() { return null; }
    public void select() { selected = true; }
    public void unselect() { selected = false; }

    public void move(int dx, int dy) {
        location.x += dx;
        location.y += dy;
    }
    public void setLocation(Point pt) { location = pt; }

    public void updateLocation(){}

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(Point pt) {
        this.width = pt.x;
        this.height = pt.y;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean contains(Point pt) {
        return pt.x > location.x && pt.x < location.x + width &&
                pt.y > location.y && pt.y < location.y + height;
    }

    public boolean findContainObject(Point pt) {
        return contains(pt);
    }

    public boolean containInArea(Point start, Point end) {
        int w = end.x - start.x;
        int h = end.y - start.y;
        return location.x > start.x && location.x + width < start.x + w &&
                location.y > start.y && location.y + height < start.y + h;
    }

    public boolean canUngroup() {
        return false;
    }

    public boolean canRename() {
        return false;
    }

}