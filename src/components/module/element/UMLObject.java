package components.module.element;

import java.awt.*;
import java.util.ArrayList;
import static utils.Config.*;

public class UMLObject {
    private ArrayList<BaseObject> objects = new ArrayList<BaseObject>();

    public UMLObject() {
        System.out.println("UMLObject initialized");
    }

    public void addObject(int mode, Point point) {
        if (mode == BUTTON_MODE.CCLASS) {
            objects.add(new ClassObject(point));
        } else if (mode == BUTTON_MODE.USECASE) {
            objects.add(new UseCaseObject(point));
        }
    }

    public void selectObject(int objectId) {
        for (BaseObject object : objects) {
            if (object.getId() == objectId) {
                object.select();
            } else {
                object.unselect();
            }
        }
    }

    abstract public class Shape extends BaseObject{
        protected String name = "";
        Shape(int id, int type){
            super(id, type);
        }
        public void setName(String name){
            this.name = name;
        }
        @Override
        public void draw(Graphics g) {
            g.setColor(Color.black);
            g.fillRect(location.x, location.y, width, height);
        }
    }

    public class ClassObject extends Shape{
        public ClassObject(Point location){
            super(BUTTON_MODE.CCLASS, BUTTON_TYPE.SHAPE);
            this.height = 100;
            this.width = 25;
            super.setName("Class");
            super.setLocation(location);
        }

        @Override
        public void draw(Graphics g) {
            super.draw(g);
            g.setColor(Color.white);
            g.drawRect(location.x, location.y, width, height);
            g.drawLine(location.x, location.y + 20, location.x + width, location.y + 20);
            g.drawLine(location.x, location.y + 40, location.x + width, location.y + 40);

            g.drawString(name, location.x + 5, location.y + 15);
        }
    }

    public class UseCaseObject extends Shape{
        // UseCaseObject is an oval shape
        public UseCaseObject(Point location){
            super(BUTTON_MODE.USECASE, BUTTON_TYPE.SHAPE);
            this.height = 100;
            this.width = 25;
            super.setName("Use Case");
            super.setLocation(location);
        }

        @Override
        public void draw(Graphics g) {
            super.draw(g);
            g.setColor(Color.white);
            g.drawOval(location.x, location.y, width, height);
            g.drawString(name, location.x + 5, location.y + 15);
        }
    }

}


