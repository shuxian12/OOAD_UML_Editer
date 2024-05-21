package components.module.element.shape;

import utils.config.ObjectType;

import java.awt.*;

public class SelectSquare extends Shape {
    public SelectSquare(Point location){
        super(ObjectType.SELECT_SQUARE.ordinal());
        super.setName("Select Square");
        super.setLocation(location);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.black);
        g.drawRect(location.x, location.y, width, height);
    }
}
