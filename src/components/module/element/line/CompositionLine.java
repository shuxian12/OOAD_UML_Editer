package components.module.element.line;

import utils.config.ObjectType;

import java.awt.*;

public class CompositionLine extends Line{
    // diamond line <>-
    public CompositionLine(){
        super(ObjectType.COMPOSITION.ordinal());
//            super.setConnection(start, end);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        int[] xPoints = {endLocation.x, endLocation.x - dx - dy, endLocation.x - 2*dx, endLocation.x - dx + dy};
        int[] yPoints = {endLocation.y, endLocation.y - dy + dx, endLocation.y - 2*dy, endLocation.y - dy - dx};
        g.setColor(Color.lightGray);
        g.fillPolygon(xPoints, yPoints, 4);
        g.setColor(Color.black);
        g.drawPolygon(xPoints, yPoints, 4);
    }
}

