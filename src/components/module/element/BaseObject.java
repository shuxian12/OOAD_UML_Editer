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

    public BaseObject(){} // Default constructor

    public BaseObject(int id, int type){
        this.id = id;
        this.type = type;
    }

    public int getId() { return id; }
    public Point getSize() { return new Point(width, height); }
    public Point getLocation() { return location; }
    public boolean getSelected() { return selected; }

    public void select() { selected = true; }
    public void unselect() { selected = false; }
    public void setLocation(Point pt) { location = pt; }
    public void setLocation(int x, int y) { location = new Point(x, y); }
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public IDraw getDrawMethod() {
        return (Graphics2D g) -> draw(g);
    }

    public boolean contains(Point pt) {
        return pt.x > location.x && pt.x < location.x + width &&
                pt.y > location.y && pt.y < location.y + height;
    }

    abstract public void draw(Graphics g);

    public int getType() {
        return type;
    }
}