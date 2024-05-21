package components.module.element.shape;

import utils.config.ButtonMode;

import java.awt.*;

public class ClassObject extends Shape{
    public ClassObject(Point location){
        super(ButtonMode.CCLASS.ordinal());
        super.setName("Class");
        super.setLocation(location);
        this.height = 100;
        this.width = 50;
        this.addPort();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.fillRect(location.x, location.y, width, height);

        g.setColor(Color.black);
        g.drawRect(location.x, location.y, width, height);
        g.drawLine(location.x, location.y + 20, location.x + width, location.y + 20);
        g.drawLine(location.x, location.y + 40, location.x + width, location.y + 40);
        g.drawString(name, location.x + 5, location.y + 15);
    }

    @Override
    public boolean contains(Point pt) {
//        System.out.println("ClassObject contains");
        int x = pt.x - location.x;
        int y = pt.y - location.y;
        return x >= 0 && x <= width && y >= 0 && y <= height;
    }
}
