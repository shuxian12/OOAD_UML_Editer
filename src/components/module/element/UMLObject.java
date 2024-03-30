package components.module.element;

import utils.IDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public void unselectALL() {
        for (BaseObject object : objects) {
            object.unselect();
        }
    }

    public ArrayList<BaseObject> getObjects() {
        return objects;
    }

    public void findObject(Point point) {
        for (BaseObject object : objects) {
            if (object.contains(point)) {
//                System.out.println("Object found");
                object.select();
            } else {
//                System.out.println("Object not found");
                object.unselect();
            }
        }
    }

    public ArrayList<IDraw> getDrawMethod() {
        ArrayList<IDraw> draws = new ArrayList<>();
        this.objects.forEach((object) -> {
            draws.add(object.getDrawMethod());
        });
        return draws;
    }


    abstract public class Shape extends BaseObject{
        protected String name = "";
        protected ArrayList<BaseObject> ports = new ArrayList<>();
        Shape(int id, int type){
            super(id, type);
        }
        public void setName(String name){
            this.name = name;
        }
        protected void addPort(){
            ports.add(new Port(this, PORT_DIRECTION.NORTH));
            ports.add(new Port(this, PORT_DIRECTION.EAST));
            ports.add(new Port(this, PORT_DIRECTION.SOUTH));
            ports.add(new Port(this, PORT_DIRECTION.WEST));
        }
        @Override
        public void draw(Graphics g) {
            g.setColor(Color.black);
            for (BaseObject port : ports) {
                port.draw(g);
            }

            g.setColor(Color.lightGray);
//            g.fillRect(location.x, location.y, width, height);
        }
    }

    public class ClassObject extends Shape{
        public ClassObject(Point location){
            super(BUTTON_MODE.CCLASS, BUTTON_TYPE.SHAPE);
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
//            System.out.println("ClassObject contains");
            int x = pt.x - location.x;
            int y = pt.y - location.y;
            return x >= 0 && x <= width && y >= 0 && y <= height;
        }
    }

    public class UseCaseObject extends Shape{
        // UseCaseObject is an oval shape
        public UseCaseObject(Point location){
            super(BUTTON_MODE.USECASE, BUTTON_TYPE.SHAPE);
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
//            System.out.println("UseCaseObject contains");
            int x = pt.x - location.x - width / 2;
            int y = pt.y - location.y - height / 2;
            return (x * x) / (width * width / 4) + (y * y) / (height * height / 4) <= 1;
        }
    }

    public class Port extends Shape{
        // Port is a small square shape that will be place in the use_cases or class to connect the lines,
        // and it will be visible when selected, and invisible when unselected.
        private BaseObject parent;
        private int direction;
        private final int PORT_SIZE = 10;
        private final Map<Integer, Point> locationMap = new HashMap<>();

        public Port(BaseObject parent, int direction){
            super(-1, -1);
            super.setName("Port");
            this.height = PORT_SIZE;
            this.width = PORT_SIZE;
            this.parent = parent;
            this.direction = direction;
            Point parentSize = parent.getSize();

//            System.out.println("Parent size: " + parentSize + " Parent location: " + parent.getLocation());
            locationMap.put(PORT_DIRECTION.NORTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
                                                            parent.getLocation().y - height));
            locationMap.put(PORT_DIRECTION.EAST, new Point(parent.getLocation().x + parentSize.x,
                                                            parent.getLocation().y + parentSize.y/2 - height/2));
            locationMap.put(PORT_DIRECTION.SOUTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
                                                            parent.getLocation().y + parentSize.y));
            locationMap.put(PORT_DIRECTION.WEST, new Point(parent.getLocation().x - width,
                                                            parent.getLocation().y + parentSize.y/2 - height/2));
            this.updateLocation();
        }

        public void updateLocation(){
            this.location = locationMap.get(direction);
//            System.out.println("Parent location: " + parent.getLocation() + " Port location: " + location);
        }

        public Point getLocation(){
            return location;
        }

        @Override
        public void draw(Graphics g) {
            updateLocation();

            if (parent.getSelected()) {
                g.setColor(Color.black);
                g.fillRect(location.x, location.y, width, height);
            }
        }
    }

}


