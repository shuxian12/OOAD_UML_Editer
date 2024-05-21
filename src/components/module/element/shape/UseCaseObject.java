package components.module.element.shape;

import utils.Config;
import utils.config.ButtonMode;
import utils.config.ObjectType;

import java.awt.*;

public class UseCaseObject extends Shape{
    // UseCaseObject is an oval shape
    public UseCaseObject(Point location){
        super(ObjectType.USECASE.ordinal());
        super.setName("Use Case");
        super.setLocation(location);
        this.height = 40;
        this.width = 100;
        this.addPort();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.fillOval(location.x, location.y, width, height);

        g.setColor(Color.black);
        g.drawOval(location.x, location.y, width, height);
        g.drawString(name, location.x + width / 5, location.y + height / 2);
    }

    @Override
    public boolean contains(Point pt) {
//        System.out.println("UseCaseObject contains");
        int x = pt.x - location.x - width / 2;
        int y = pt.y - location.y - height / 2;
        return (x * x) / (width * width / 4) + (y * y) / (height * height / 4) <= 1;
    }
}
