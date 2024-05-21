package components.module.element.line;

import utils.config.ObjectType;

import java.awt.*;

public class GeneralizationLine extends Line{
    // triangle <|-
    public GeneralizationLine(){
        super(ObjectType.GENERALIZATION.ordinal());
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        int[] xPoints = {endLocation.x, endLocation.x - dx - dy, endLocation.x - dx + dy};
        int[] yPoints = {endLocation.y, endLocation.y - dy + dx, endLocation.y - dy - dx};
        g.setColor(Color.lightGray);
        g.fillPolygon(xPoints, yPoints, 3);
        g.setColor(Color.black);
        g.drawPolygon(xPoints, yPoints, 3);
    }
}

