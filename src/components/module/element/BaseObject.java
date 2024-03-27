package components.module.element;

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
    public int getType() { return type; }
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

    abstract public void draw(Graphics g);
}