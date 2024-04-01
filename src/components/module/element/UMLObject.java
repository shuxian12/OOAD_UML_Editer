package components.module.element;

import utils.IDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static utils.Config.*;

public class UMLObject {
    private final ArrayList<Base> objects = new ArrayList<>();

    public UMLObject() {
        System.out.println("UMLObject initialized");
    }

    public Base addObject(int mode, Point point) {
        Base object = getTypeObject(mode, point);
        if (object != null) {
            objects.add(object);
            return object;
        }
        return null;
    }

    public Line addLine(int mode) {
        Line object = getTypeLine(mode);
        if (object != null) {
            objects.add(object);
            return object;
        }
        return null;
    }

    public Base getTypeObject(int mode, Point point) {
        switch (mode) {
            case BUTTON_MODE.CCLASS:
                return new ClassObject(point);
            case BUTTON_MODE.USECASE:
                return new UseCaseObject(point);
            case BUTTON_MODE.SELECT:
                return new SelectSquare(point);
            default:
                return null;
        }
    }

    public Line getTypeLine(int mode) {
        switch (mode) {
            case BUTTON_MODE.ASSOCIATION:
                return new AssociationLine();
            case BUTTON_MODE.GENERALIZATION:
                return new GeneralizationLine();
            case BUTTON_MODE.COMPOSITION:
                return new CompositionLine();
            default:
                return null;
        }
    }

    public void unselectALL() {
        for (Base object : objects) {
            object.unselect();
        }
    }

    public ArrayList<Base> getObjects() {
        return objects;
    }

    public Base findObject(Point point) {
        for (Base object : objects) {
            if (object.contains(point)) {
                object.select();
                return object;
            }
        }
        return null;
    }

    public Shape findContainObject(Point point) {
        for (Base object : objects) {
            if (object.contains(point)) {
                return (Shape) object;
            }
        }
        return null;
    }

    public ArrayList<IDraw> getDrawMethod() {
        ArrayList<IDraw> draws = new ArrayList<>();
        this.objects.forEach((object) -> {
            draws.add(object.getDrawMethod());
            System.out.println("Object added to draw: " + object.getId());
        });
        return draws;
    }

    public Port findPort(Point point) {
        Shape object = findContainObject(point);
        if (object != null) {
            System.out.println("Pressed on object");
            return (Port) object.findPort(point);
        }
        System.out.println("Pressed on empty space");
        return null;
    }

    public void removeObject(Base object) {
        objects.remove(object);
    }

    public ArrayList<Base> setAreaObjects(Point topCorner, Point bottomCorner) {
        ArrayList<Base> targets = new ArrayList<>();
        for(Base object: getObjects()) {
            if(object.containInArea(topCorner, bottomCorner)) {
                targets.add(object);
                object.select();
            }
        }
        return targets;
    }

    public void doAction(int action, Base object) {
        switch (action) {
            case MENU_CONFIG.GROUP:
                group();
                break;
            case MENU_CONFIG.UNGROUP:
                ungroup(object);
                break;
            case MENU_CONFIG.RENAME:
                rename();
                break;
            default:
                System.out.println("Warning: Get Unsupported MenuItemId at Presenter.onPressed()");
                break;
        }
    }

    private void group() {
        System.out.println("Grouping");
        ArrayList<Base> selectedObjects = getSelectedObject();
        Point topCorner = getTopCorner(selectedObjects);
        Point bottomCorner = getBottomCorner(selectedObjects);
        Group group = new Group(topCorner, bottomCorner, selectedObjects);
        objects.add(group);
    }

    private void ungroup(Base target) {
        objects.remove(target);
    }

    private void rename() {
        System.out.println("Rename");
    }

    private ArrayList<Base> getSelectedObject() {
        ArrayList<Base> selected = new ArrayList<>();
        for (Base object : getObjects()) {
            if (object.getSelected()) {
                selected.add(object);
            }
        }
        return selected;
    }

    private Point getTopCorner(ArrayList<Base> selectedObjects) {
        Point topCorner = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (Base object : selectedObjects) {
            topCorner.x = Math.min(topCorner.x, object.getLocation().x);
            topCorner.y = Math.min(topCorner.y, object.getLocation().y);
        }
        return topCorner;
    }

    private Point getBottomCorner(ArrayList<Base> selectedObjects) {
        Point bottomCorner = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for (Base object : selectedObjects) {
            bottomCorner.x = Math.max(bottomCorner.x, object.getLocation().x + object.getSize().x);
            bottomCorner.y = Math.max(bottomCorner.y, object.getLocation().y + object.getSize().y);
        }
        return bottomCorner;
    }


    abstract public static class Base extends BaseObject{
        Base(int id, int type){
            super(id, type);
        }
    }

    abstract public class Shape extends Base{
        protected String name = "";
        protected ArrayList<Base> ports = new ArrayList<>();
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
        public Base findPort(Point point){
            int centerX = location.x + width / 2;
            int centerY = location.y + height / 2;
            Point normPoint = new Point(point.x - centerX, point.y - centerY);

            if (normPoint.x + normPoint.y > 0) {
                if (normPoint.x - normPoint.y > 0) {
                    return ports.get(PORT_DIRECTION.EAST);
                }
                return ports.get(PORT_DIRECTION.SOUTH);
            } else {
                if (normPoint.x - normPoint.y > 0) {
                    return ports.get(PORT_DIRECTION.NORTH);
                }
                return ports.get(PORT_DIRECTION.WEST);
            }
        }
        @Override
        public void draw(Graphics g) {
            g.setColor(Color.black);
            for (Base port : ports) {
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
            System.out.println("ClassObject contains");
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
            System.out.println("UseCaseObject contains");
            int x = pt.x - location.x - width / 2;
            int y = pt.y - location.y - height / 2;
            return (x * x) / (width * width / 4) + (y * y) / (height * height / 4) <= 1;
        }
    }

    public class Port extends Shape{
        // Port is a small square shape that will be place in the use_cases or class to connect the lines,
        // and it will be visible when selected, and invisible when unselected.
        private final Base parent;
        private final int direction;
        private final int PORT_SIZE = 10;
        private final Map<Integer, Point> locationMap = new HashMap<>();

        public Port(Base parent, int direction){
            super(OBJECT_TYPE.PORT, OBJECT_TYPE.PORT);
            super.setName("Port");
            this.height = PORT_SIZE;
            this.width = PORT_SIZE;
            this.parent = parent;
            this.direction = direction;

            this.updateMap();
//            System.out.println("Parent size: " + parentSize + " Parent location: " + parent.getLocation());
//            locationMap.put(PORT_DIRECTION.NORTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
//                                                            parent.getLocation().y - height));
//            locationMap.put(PORT_DIRECTION.EAST, new Point(parent.getLocation().x + parentSize.x,
//                                                            parent.getLocation().y + parentSize.y/2 - height/2));
//            locationMap.put(PORT_DIRECTION.SOUTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
//                                                            parent.getLocation().y + parentSize.y));
//            locationMap.put(PORT_DIRECTION.WEST, new Point(parent.getLocation().x - width,
//                                                            parent.getLocation().y + parentSize.y/2 - height/2));
            this.updateLocation();
        }

        private void updateMap() {
            Point parentSize = parent.getSize();
            locationMap.put(PORT_DIRECTION.NORTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
                                                            parent.getLocation().y - height));
            locationMap.put(PORT_DIRECTION.EAST, new Point(parent.getLocation().x + parentSize.x,
                                                            parent.getLocation().y + parentSize.y/2 - height/2));
            locationMap.put(PORT_DIRECTION.SOUTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
                                                            parent.getLocation().y + parentSize.y));
            locationMap.put(PORT_DIRECTION.WEST, new Point(parent.getLocation().x - width,
                                                            parent.getLocation().y + parentSize.y/2 - height/2));
        }

        public void updateLocation(){
            this.updateMap();
            this.location = locationMap.get(direction);
//            System.out.println("Parent location: " + parent.getLocation() + " Port location: " + location);
        }

        @Override
        public Point getLocation() {
            if (direction == PORT_DIRECTION.NORTH) {
                return new Point(location.x + width / 2, location.y + height);
            } else if (direction == PORT_DIRECTION.WEST) {
                return new Point(location.x + width, location.y + height / 2);
            }
            return super.getLocation();
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

    abstract public class Line extends Base{
        protected Base startPort;
        protected Base endPort;
        protected Point startLocation;
        protected Point endLocation;
        protected final int ARROW_SIZE = 10;
        protected int dx, dy;
        Line(int id, int type){
            super(id, type);
        }
        public void setConnection(Port start, Port end){
            this.startPort = start;
            this.endPort = end;
        }

        public void setLocation(Point start, Point end){
            this.startLocation = start;
            this.endLocation = end;
        }
        @Override
        public void draw(Graphics g) {
            if (this.endPort != null)
                this.setLocation(this.startPort.getLocation(), this.endPort.getLocation());
            double angle = Math.atan2(endLocation.y - startLocation.y, endLocation.x - startLocation.x);
            dx = (int) (ARROW_SIZE * Math.cos(angle));
            dy = (int) (ARROW_SIZE * Math.sin(angle));

            g.setColor(Color.black);
            g.drawLine(startLocation.x, startLocation.y, endLocation.x, endLocation.y);
        }
    }

    public class AssociationLine extends Line{
        // arrow
        public AssociationLine(){
            super(BUTTON_MODE.ASSOCIATION, BUTTON_TYPE.LINE);
        }

        @Override
        public void draw(Graphics g) {
            super.draw(g);

            g.drawLine(endLocation.x, endLocation.y, endLocation.x - dx - dy, endLocation.y - dy + dx);
            g.drawLine(endLocation.x, endLocation.y, endLocation.x - dx + dy, endLocation.y - dy - dx);
        }
    }

    public class GeneralizationLine extends Line{
        // triangle <|-
        public GeneralizationLine(){
            super(BUTTON_MODE.GENERALIZATION, BUTTON_TYPE.LINE);
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

    public class CompositionLine extends Line{
        // diamond line <>-
        public CompositionLine(){
            super(BUTTON_MODE.COMPOSITION, BUTTON_TYPE.LINE);
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

    public class SelectSquare extends Shape {
        private Point startLocation, endLocation;
        public SelectSquare(Point location){
            super(OBJECT_TYPE.SELECT_SQUARE, OBJECT_TYPE.SELECT_SQUARE);
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

    public class Group extends Shape {
        private ArrayList<Base> groupObjects;
        private Point startLocation;
        private Point endLocation;
        public Group(Point startLocation, Point endLocation, ArrayList<Base> groupObjects){
            super(OBJECT_TYPE.GROUP, OBJECT_TYPE.GROUP);
            super.setName("Group");
            this.setLocation(startLocation, endLocation);
            this.groupObjects = groupObjects;
        }

        public void setLocation(Point start, Point end){
            this.startLocation = start;
            this.endLocation = end;
        }

//        public void addObject(Base object){
//            groupObjects.add(object);
//        }
//
//        public void removeObject(Base object){
//            groupObjects.remove(object);
//        }

        @Override
        public void draw(Graphics g) {
            super.draw(g);
            g.setColor(Color.black);
            g.drawRect(startLocation.x, startLocation.y,
                    endLocation.x - startLocation.x, endLocation.y - startLocation.y);
        }
    }

}


