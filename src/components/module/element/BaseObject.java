package components.module.element;

import utils.IDraw;

import java.awt.*;

abstract class BaseObject{
    protected Point location = new Point();
    protected int width;
    protected int height;
    protected int id;
    protected int type;
    protected boolean selected = false;
    protected boolean isGroup = false;

    public BaseObject(){} // Default constructor

    public BaseObject(int id, int type){
        this.id = id;
        this.type = type;
    }

    abstract public void draw(Graphics g);

    public int getId() { return id; }
    public Point getSize() { return new Point(width, height); }
    public Point getLocation() { return location; }
    public int getType() { return type; }
    public boolean getSelected() { return selected; }
    public void select() { selected = true; }
    public void unselect() { selected = false; }
    public void setLocation(Point pt) { location = pt; }
    public void move(int dx, int dy) {
        location.x += dx;
        location.y += dy;
    }
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(Point pt) {
        this.width = pt.x;
        this.height = pt.y;
    }

    public IDraw getDrawMethod() {
        return (Graphics2D g) -> draw(g);
    }

    public boolean contains(Point pt) {
        return pt.x > location.x && pt.x < location.x + width &&
                pt.y > location.y && pt.y < location.y + height;
    }

    public boolean containInArea(Point start, Point end) {
        int w = end.x - start.x;
        int h = end.y - start.y;
        return location.x > start.x && location.x + width < start.x + w &&
                location.y > start.y && location.y + height < start.y + h;
    }

}