package components.module.element;

import components.module.element.line.*;
import components.module.element.shape.*;
import components.module.element.shape.Shape;
import utils.IDraw;
import utils.config.ObjectType;

import java.awt.*;
import java.util.*;

public class UMLObject {
    private final ArrayList<BaseObject> objects = new ArrayList<>() {
        @Override
        public boolean add(BaseObject base) {
            base.setDepth(getNextDepth());
            super.add(base);
            sort(new UMLComparator());
            return true;
        }
    };

    private class UMLComparator implements Comparator<BaseObject> {
        // descending order
        @Override
        public int compare(BaseObject o1, BaseObject o2) {
            if (o1.depth == o2.depth) {
                return 0;
            }
            return o1.depth > o2.depth ? 1 : -1;
        }
    }

    public UMLObject() {
        System.out.println("UMLObject initialized");
    }

    public BaseObject addObject(int mode, Point point) {
        BaseObject object = getTypeObject(mode, point);
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

    private int getNextDepth() {
        int minDepth = 100;
        for (BaseObject object : objects) {
            minDepth = Math.min(minDepth, object.depth);
        }
        return minDepth - 1;
    }

    private BaseObject getTypeObject(int mode, Point point) {
        return ObjectType.getType(mode).getConstructor(point);
    }

    private Line getTypeLine(int mode) {
        return (Line) ObjectType.getType(mode).getConstructor(null);
    }

    public void unselectALL() {
        for (BaseObject object : objects) {
            object.unselect();
        }
    }

    public ArrayList<BaseObject> getObjects() {
        return objects;
    }

    public BaseObject findObject(Point point) {
        for (BaseObject object : objects) {
            if (object.contains(point) && !object.isGroup){
                object.select();
                return object;
            }
        }
        return null;
    }

    public Shape findContainObject(Point point) {
        for (BaseObject object : objects) {
            if (object.findContainObject(point)) {
                return (Shape) object;
            }
        }
        return null;
    }

    public ArrayList<IDraw> getDrawMethod() {
        ArrayList<IDraw> draws = new ArrayList<>();
        ListIterator<BaseObject> iterator = objects.listIterator(objects.size());
        while (iterator.hasPrevious()) {
            BaseObject object = iterator.previous();
            draws.add(object.getDrawMethod());
        }
        return draws;
    }

    public Port findPort(Point point) {
        Shape object = findContainObject(point);
        if (object != null){
            System.out.println("Pressed on object");
            return (Port) object.findPort(point);
        }
        System.out.println("Pressed on empty space");
        return null;
    }

    public void removeObject(BaseObject object) {
        objects.remove(object);
    }

    public ArrayList<BaseObject> setAreaObjects(Point topCorner, Point bottomCorner) {
        ArrayList<BaseObject> targets = new ArrayList<>();
        for(BaseObject object: getObjects()) {
            if(object.containInArea(topCorner, bottomCorner) && !object.isGroup) {
                targets.add(object);
                object.select();
            }
        }
        return targets;
    }

    public void group() {
        System.out.println("Grouping");
        ArrayList<BaseObject> selectedObjects = getSelectedObject();
        if (selectedObjects.size() < 2) {
            return;
        }
        Point topCorner = getTopCorner(selectedObjects);
        Point bottomCorner = getBottomCorner(selectedObjects);
        Group group = new Group(topCorner, bottomCorner, selectedObjects);
        objects.add(group);
    }

    public void ungroup() {
        System.out.println("Ungrouping");
        ArrayList<BaseObject> selectedObjects = getSelectedObject();
        if (selectedObjects.size() != 1 || !selectedObjects.get(0).canUngroup()) {
            return;
        }

        for (BaseObject object : selectedObjects.get(0).getInnerObjects()) {
            object.setGroup(false);
        }
        objects.remove(selectedObjects.get(0));
        this.unselectALL();
    }

    public void rename(String newName) {
        System.out.println("Rename");
        ArrayList<BaseObject> selectedObjects = getSelectedObject();
        selectedObjects.get(0).setName(newName);
    }

    public boolean checkRenameValid() {
        ArrayList<BaseObject> selectedObjects = getSelectedObject();
        return selectedObjects.size() == 1
                && selectedObjects.get(0).canRename();
    }

    private ArrayList<BaseObject> getSelectedObject() {
        ArrayList<BaseObject> selected = new ArrayList<>();
        for (BaseObject object : getObjects()) {
            if (object.getSelected()) {
                selected.add(object);
            }
        }
        return selected;
    }

    private Point getTopCorner(ArrayList<BaseObject> selectedObjects) {
        Point topCorner = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (BaseObject object : selectedObjects) {
            topCorner.x = Math.min(topCorner.x, object.getLocation().x);
            topCorner.y = Math.min(topCorner.y, object.getLocation().y);
        }
        return topCorner;
    }

    private Point getBottomCorner(ArrayList<BaseObject> selectedObjects) {
        Point bottomCorner = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for (BaseObject object : selectedObjects) {
            bottomCorner.x = Math.max(bottomCorner.x, object.getBottomCorner().x);
            bottomCorner.y = Math.max(bottomCorner.y, object.getBottomCorner().y);
        }
        return bottomCorner;
    }

//
//    abstract public static class BaseObject extends BaseObjectObject{
//        BaseObject(int id, int type){
//            super(id, type);
//        }
//    }
//
//    abstract public class Shape extends BaseObject{
//        protected String name = "";
//        protected ArrayList<BaseObject> ports = new ArrayList<>();
//        Shape(int id, int type){
//            super(id, type);
//        }
//        public void setName(String name){
//            this.name = name;
//        }
//        protected void addPort(){
//            ports.add(new Port(this, PortDirection.NORTH));
//            ports.add(new Port(this, PortDirection.EAST));
//            ports.add(new Port(this, PortDirection.SOUTH));
//            ports.add(new Port(this, PortDirection.WEST));
//        }
//        public BaseObject findPort(Point point){
//            int centerX = location.x + width / 2;
//            int centerY = location.y + height / 2;
//            Point normPoint = new Point(point.x - centerX, point.y - centerY);
//
//            if (normPoint.x + normPoint.y > 0) {
//                if (normPoint.x - normPoint.y > 0) {
//                    return ports.get(PortDirection.EAST.ordinal());
//                }
//                return ports.get(PortDirection.SOUTH.ordinal());
//            } else {
//                if (normPoint.x - normPoint.y > 0) {
//                    return ports.get(PortDirection.NORTH.ordinal());
//                }
//                return ports.get(PortDirection.WEST.ordinal());
//            }
//        }
//        @Override
//        public void draw(Graphics g) {
//            g.setColor(Color.black);
//            for (BaseObject port : ports) {
//                port.updateLocation();
//                if (!this.isGroup){
//                    port.draw(g);
//                }
//            }
//
//            g.setColor(Color.lightGray);
////            g.fillRect(location.x, location.y, width, height);
//        }
//
//        @Override
//        public void move(int dx, int dy) {
//            super.move(dx, dy);
////            System.out.println("Shape move from " + location + " to " + new Point(location.x + dx, location.y + dy));
//        }
//    }
//
//    public class ClassObject extends Shape{
//        public ClassObject(Point location){
//            super(ButtonMode.CCLASS.ordinal(), BUTTON_TYPE.SHAPE);
//            super.setName("Class");
//            super.setLocation(location);
//            this.height = 100;
//            this.width = 50;
//            this.addPort();
//        }
//
//        @Override
//        public void draw(Graphics g) {
//            super.draw(g);
//            g.fillRect(location.x, location.y, width, height);
//
//            g.setColor(Color.black);
//            g.drawRect(location.x, location.y, width, height);
//            g.drawLine(location.x, location.y + 20, location.x + width, location.y + 20);
//            g.drawLine(location.x, location.y + 40, location.x + width, location.y + 40);
//            g.drawString(name, location.x + 5, location.y + 15);
//        }
//
//        @Override
//        public boolean contains(Point pt) {
//            System.out.println("ClassObject contains");
//            int x = pt.x - location.x;
//            int y = pt.y - location.y;
//            return x >= 0 && x <= width && y >= 0 && y <= height;
//        }
//    }
//
//    public class UseCaseObject extends Shape{
//        // UseCaseObject is an oval shape
//        public UseCaseObject(Point location){
//            super(ButtonMode.USECASE.ordinal(), BUTTON_TYPE.SHAPE);
//            super.setName("Use Case");
//            super.setLocation(location);
//            this.height = 40;
//            this.width = 100;
//            this.addPort();
//        }
//
//        @Override
//        public void draw(Graphics g) {
//            super.draw(g);
//            g.fillOval(location.x, location.y, width, height);
//
//            g.setColor(Color.black);
//            g.drawOval(location.x, location.y, width, height);
//            g.drawString(name, location.x + width / 5, location.y + height / 2);
//        }
//
//        @Override
//        public boolean contains(Point pt) {
//            System.out.println("UseCaseObject contains");
//            int x = pt.x - location.x - width / 2;
//            int y = pt.y - location.y - height / 2;
//            return (x * x) / (width * width / 4) + (y * y) / (height * height / 4) <= 1;
//        }
//    }
//
//    public class Port extends Shape{
//        // Port is a small square shape that will be place in the use_cases or class to connect the lines,
//        // and it will be visible when selected, and invisible when unselected.
//        private final BaseObject parent;
//        private final PortDirection direction;
//        private final int PORT_SIZE = 10;
//        private final Map<PortDirection, Point> locationMap = new HashMap<>();
//
//        public Port(BaseObject parent, PortDirection direction){
//            super(OBJECT_TYPE.PORT, OBJECT_TYPE.PORT);
//            super.setName("Port");
//            this.height = PORT_SIZE;
//            this.width = PORT_SIZE;
//            this.parent = parent;
//            this.direction = direction;
//
//            this.updateMap();
////            System.out.println("Parent size: " + parentSize + " Parent location: " + parent.getLocation());
////            locationMap.put(PortDirection.NORTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
////                                                            parent.getLocation().y - height));
////            locationMap.put(PortDirection.EAST, new Point(parent.getLocation().x + parentSize.x,
////                                                            parent.getLocation().y + parentSize.y/2 - height/2));
////            locationMap.put(PortDirection.SOUTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
////                                                            parent.getLocation().y + parentSize.y));
////            locationMap.put(PortDirection.WEST, new Point(parent.getLocation().x - width,
////                                                            parent.getLocation().y + parentSize.y/2 - height/2));
//            this.updateLocation();
//        }
//
//        private void updateMap() {
//            Point parentSize = parent.getSize();
//            locationMap.put(PortDirection.NORTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
//                                                            parent.getLocation().y - height));
//            locationMap.put(PortDirection.EAST, new Point(parent.getLocation().x + parentSize.x,
//                                                            parent.getLocation().y + parentSize.y/2 - height/2));
//            locationMap.put(PortDirection.SOUTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
//                                                            parent.getLocation().y + parentSize.y));
//            locationMap.put(PortDirection.WEST, new Point(parent.getLocation().x - width,
//                                                            parent.getLocation().y + parentSize.y/2 - height/2));
//        }
//
//        @Override
//        public void updateLocation(){
//            this.updateMap();
//            this.location = locationMap.get(direction);
////            System.out.println("Parent location: " + parent.getLocation() + " Port location: " + location);
//        }
//
//        @Override
//        public Point getLocation() {
//            if (direction == PortDirection.NORTH) {
//                return new Point(location.x + width / 2, location.y + height);
//            } else if (direction == PortDirection.WEST) {
//                return new Point(location.x + width, location.y + height / 2);
//            }
//            return super.getLocation();
//        }
//
//        @Override
//        public void draw(Graphics g) {
//            updateLocation();
//
//            if (parent.getSelected()) {
//                g.setColor(Color.black);
//                g.fillRect(location.x, location.y, width, height);
//            }
//        }
//
//        public BaseObject getParent(){
//            return parent;
//        }
//    }
//
//    abstract public class Line extends BaseObject{
//        protected BaseObject startPort;
//        protected BaseObject endPort;
//        protected Point startLocation;
//        protected Point endLocation;
//        protected final int ARROW_SIZE = 10;
//        protected int dx, dy;
//        Line(int id, int type){
//            super(id, type);
//        }
//        public void setConnection(Port start, Port end){
//            this.startPort = start;
//            this.endPort = end;
//        }
//
//        public void setLocation(Point start, Point end){
//            this.startLocation = start;
//            this.endLocation = end;
//        }
//        @Override
//        public void draw(Graphics g) {
//            if (this.endPort != null)
//                this.setLocation(this.startPort.getLocation(), this.endPort.getLocation());
//            double angle = Math.atan2(endLocation.y - startLocation.y, endLocation.x - startLocation.x);
//            dx = (int) (ARROW_SIZE * Math.cos(angle));
//            dy = (int) (ARROW_SIZE * Math.sin(angle));
//
//            g.setColor(Color.black);
//            g.drawLine(startLocation.x, startLocation.y, endLocation.x, endLocation.y);
//        }
//    }
//
//    public class AssociationLine extends Line{
//        // arrow
//        public AssociationLine(){
//            super(ButtonMode.ASSOCIATION.ordinal(), BUTTON_TYPE.LINE);
//        }
//
//        @Override
//        public void draw(Graphics g) {
//            super.draw(g);
//
//            g.drawLine(endLocation.x, endLocation.y, endLocation.x - dx - dy, endLocation.y - dy + dx);
//            g.drawLine(endLocation.x, endLocation.y, endLocation.x - dx + dy, endLocation.y - dy - dx);
//        }
//    }
//
//    public class GeneralizationLine extends Line{
//        // triangle <|-
//        public GeneralizationLine(){
//            super(ButtonMode.GENERALIZATION.ordinal(), BUTTON_TYPE.LINE);
//        }
//
//        @Override
//        public void draw(Graphics g) {
//            super.draw(g);
//
//            int[] xPoints = {endLocation.x, endLocation.x - dx - dy, endLocation.x - dx + dy};
//            int[] yPoints = {endLocation.y, endLocation.y - dy + dx, endLocation.y - dy - dx};
//            g.setColor(Color.lightGray);
//            g.fillPolygon(xPoints, yPoints, 3);
//            g.setColor(Color.black);
//            g.drawPolygon(xPoints, yPoints, 3);
//        }
//    }
//
//    public class CompositionLine extends Line{
//        // diamond line <>-
//        public CompositionLine(){
//            super(ButtonMode.COMPOSITION.ordinal(), BUTTON_TYPE.LINE);
////            super.setConnection(start, end);
//        }
//
//        @Override
//        public void draw(Graphics g) {
//            super.draw(g);
//
//            int[] xPoints = {endLocation.x, endLocation.x - dx - dy, endLocation.x - 2*dx, endLocation.x - dx + dy};
//            int[] yPoints = {endLocation.y, endLocation.y - dy + dx, endLocation.y - 2*dy, endLocation.y - dy - dx};
//            g.setColor(Color.lightGray);
//            g.fillPolygon(xPoints, yPoints, 4);
//            g.setColor(Color.black);
//            g.drawPolygon(xPoints, yPoints, 4);
//        }
//    }
//
//    public class SelectSquare extends Shape {
//        public SelectSquare(Point location){
//            super(OBJECT_TYPE.SELECT_SQUARE, OBJECT_TYPE.SELECT_SQUARE);
//            super.setName("Select Square");
//            super.setLocation(location);
//        }
//
//        @Override
//        public void draw(Graphics g) {
//            super.draw(g);
//            g.setColor(Color.black);
//            g.drawRect(location.x, location.y, width, height);
//        }
//    }
//
//    public class Group extends Shape {
//        private ArrayList<BaseObject> groupObjects;
//        private Point startLocation;
//        private Point endLocation;
//        public Group(Point startLocation, Point endLocation, ArrayList<BaseObject> groupObjects){
//            super(OBJECT_TYPE.GROUP, OBJECT_TYPE.GROUP);
//            super.setName("Group");
//            this.setLocation(startLocation, endLocation);
//            this.groupObjects = groupObjects;
//            this.setGroupType();
//        }
//
//        public void setLocation(Point start, Point end){
//            this.startLocation = start;
//            this.endLocation = end;
//        }
//
//        private void setGroupType(){
//            for (BaseObject object : groupObjects) {
//                object.isGroup = true;
//            }
//        }
//
//        @Override
//        public Point getLocation() {
//            return startLocation;
//        }
//
//        @Override
//        public void draw(Graphics g) {
//            super.draw(g);
//            if (getSelected() && !isGroup) {
//                g.setColor(Color.black);
//            }
//            else {
//                g.setColor(Color.lightGray);
//            }
//            g.drawRect(startLocation.x, startLocation.y,
//                    endLocation.x - startLocation.x, endLocation.y - startLocation.y);
//
//            for (BaseObject object : groupObjects) {
//                object.draw(g);
//            }
//        }
//
//        @Override
//        public void move(int dx, int dy) {
////            System.out.println("Group move from " + startLocation + "," + endLocation + " to " +
////                    new Point(startLocation.x + dx, startLocation.y + dy) + ", " + new Point(endLocation.x + dx, endLocation.y + dy));
//            this.startLocation.x += dx;
//            this.startLocation.y += dy;
//            this.endLocation.x += dx;
//            this.endLocation.y += dy;
//            for (BaseObject object : groupObjects) {
//                object.move(dx, dy);
//            }
//        }
//
//        @Override
//        public boolean contains(Point pt) {
//            for(BaseObject object: groupObjects) {
//                if (object.contains(pt)) {
//                    return true;
//                }
//            }
//            return false;
////            return pt.x > startLocation.x && pt.x < endLocation.x &&
////                    pt.y > startLocation.y && pt.y < endLocation.y;
//
//        }
//
//        @Override
//        public boolean containInArea(Point start, Point end) {
//            return startLocation.x > start.x && endLocation.x < end.x &&
//                    startLocation.y > start.y && endLocation.y < end.y;
//        }
//    }

}


