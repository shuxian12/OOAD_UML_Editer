package components.module.element.line;

import utils.config.ObjectType;

import java.awt.*;

public class AssociationLine extends Line{
    // arrow
    public AssociationLine(){
        super(ObjectType.ASSOCIATION.ordinal());
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        g.drawLine(endLocation.x, endLocation.y, endLocation.x - dx - dy, endLocation.y - dy + dx);
        g.drawLine(endLocation.x, endLocation.y, endLocation.x - dx + dy, endLocation.y - dy - dx);
    }
}

