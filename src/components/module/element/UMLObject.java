package components.module.element;

import utils.IDraw;
import utils.config.ButtonMode;
import utils.config.PortDirection;

import java.awt.*;
import java.util.*;

import static utils.Config.*;

public class UMLObject {
    private final ArrayList<Base> objects = new ArrayList<>() {
        @Override
        public boolean add(Base base) {
            base.setDepth(getNextDepth());
            super.add(base);
            sort(new UMLComparator());
            return true;
        }
    };

    private class UMLComparator implements Comparator<Base> {
        // descending order
        @Override
        public int compare(Base o1, Base o2) {
            if (o1.depth == o2.depth) {
                return 0;
            }
            return o1.depth > o2.depth ? 1 : -1;
        }
    }

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

    private int getNextDepth() {
        int minDepth = 100;
        for (Base object : objects) {
            minDepth = Math.min(minDepth, object.depth);
        }
        return minDepth - 1;
    }

    private Base getTypeObject(int mode, Point point) {
        switch (ButtonMode.getMode(mode)) {
            case CCLASS:
                return new ClassObject(point);
            case USECASE:
                return new UseCaseObject(point);
            case SELECT:
                return new SelectSquare(point);
            default:
                return null;
        }
    }

    public Line getTypeLine(int mode) {
        switch (ButtonMode.getMode(mode)) {
            case ASSOCIATION:
                return new AssociationLine();
            case GENERALIZATION:
                return new GeneralizationLine();
            case COMPOSITION:
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
            if (object.contains(point) && !object.isGroup){
                object.select();
                return object;
            }
        }
        return null;
    }

    public Shape findContainObject(Point point) {
        for (Base object : objects) {
//            if (object.contains(point) && !object.isGroup) {
//            if (object.getType() != OBJECT_TYPE.GROUP && object.contains(point)) {
            if (!(object instanceof Group) && object.contains(point)) {
                return (Shape) object;
            }
        }
        return null;
    }

    public ArrayList<IDraw> getDrawMethod() {
        ArrayList<IDraw> draws = new ArrayList<>();
        ListIterator<Base> iterator = objects.listIterator(objects.size());
        while (iterator.hasPrevious()) {
            Base object = iterator.previous();
            draws.add(object.getDrawMethod());
//            System.out.println("Object added to draw: " + object.getId());
        }
        return draws;
    }

    public Port findPort(Point point) {
        Shape object = findContainObject(point);
//        if (object != null && object.getType() != OBJECT_TYPE.GROUP){
        if (object != null){
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
            if(object.containInArea(topCorner, bottomCorner) && !object.isGroup) {
                targets.add(object);
                object.select();
            }
        }
        return targets;
    }

//    public void doAction(int action, Base object) {
//        switch (action) {
//            case MENU_CONFIG.GROUP:
//                group();
//                break;
//            case MENU_CONFIG.UNGROUP:
//                ungroup();
//                break;
//            case MENU_CONFIG.RENAME:
//                rename();
//                break;
//            default:
//                System.out.println("Warning: Get Unsupported MenuItemId at Presenter.onPressed()");
//                break;
//        }
//    }

    public void group() {
        System.out.println("Grouping");
        ArrayList<Base> selectedObjects = getSelectedObject();
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
        ArrayList<Base> selectedObjects = getSelectedObject();
        if (selectedObjects.size() != 1 ||
//                selectedObjects.get(0).getType() != OBJECT_TYPE.GROUP) {
                !(selectedObjects.get(0) instanceof Group)) {
            return;
        }

        for (Base object : ((Group) selectedObjects.get(0)).groupObjects) {
            object.isGroup = false;
        }
        objects.remove(selectedObjects.get(0));
        this.unselectALL();
    }

    public void rename(String newName) {
        System.out.println("Rename");
        ArrayList<Base> selectedObjects = getSelectedObject();
        ((Shape) selectedObjects.get(0)).setName(newName);
    }

    public boolean checkRenameValid() {
        ArrayList<Base> selectedObjects = getSelectedObject();
        return selectedObjects.size() == 1
                && selectedObjects.get(0) instanceof Shape;
//                && selectedObjects.get(0).getType() == BUTTON_TYPE.SHAPE;
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
//            if (object.getType() != OBJECT_TYPE.GROUP) {
            if (!(object instanceof Group)) {
                bottomCorner.x = Math.max(bottomCorner.x, object.getLocation().x + object.getSize().x);
                bottomCorner.y = Math.max(bottomCorner.y, object.getLocation().y + object.getSize().y);
            }
            else {
                bottomCorner.x = Math.max(bottomCorner.x, ((Group) object).endLocation.x);
                bottomCorner.y = Math.max(bottomCorner.y, ((Group) object).endLocation.y);
            }
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
            ports.add(new Port(this, PortDirection.NORTH));
            ports.add(new Port(this, PortDirection.EAST));
            ports.add(new Port(this, PortDirection.SOUTH));
            ports.add(new Port(this, PortDirection.WEST));
        }
        public Base findPort(Point point){
            int centerX = location.x + width / 2;
            int centerY = location.y + height / 2;
            Point normPoint = new Point(point.x - centerX, point.y - centerY);

            if (normPoint.x + normPoint.y > 0) {
                if (normPoint.x - normPoint.y > 0) {
                    return ports.get(PortDirection.EAST.ordinal());
                }
                return ports.get(PortDirection.SOUTH.ordinal());
            } else {
                if (normPoint.x - normPoint.y > 0) {
                    return ports.get(PortDirection.NORTH.ordinal());
                }
                return ports.get(PortDirection.WEST.ordinal());
            }
        }
        @Override
        public void draw(Graphics g) {
            g.setColor(Color.black);
            for (Base port : ports) {
                port.updateLocation();
                if (!this.isGroup){
                    port.draw(g);
                }
            }

            g.setColor(Color.lightGray);
//            g.fillRect(location.x, location.y, width, height);
        }

        @Override
        public void move(int dx, int dy) {
            super.move(dx, dy);
//            System.out.println("Shape move from " + location + " to " + new Point(location.x + dx, location.y + dy));
        }
    }

    public class ClassObject extends Shape{
        public ClassObject(Point location){
            super(ButtonMode.CCLASS.ordinal(), BUTTON_TYPE.SHAPE);
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
            super(ButtonMode.USECASE.ordinal(), BUTTON_TYPE.SHAPE);
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
        private final PortDirection direction;
        private final int PORT_SIZE = 10;
        private final Map<PortDirection, Point> locationMap = new HashMap<>();

        public Port(Base parent, PortDirection direction){
            super(OBJECT_TYPE.PORT, OBJECT_TYPE.PORT);
            super.setName("Port");
            this.height = PORT_SIZE;
            this.width = PORT_SIZE;
            this.parent = parent;
            this.direction = direction;

            this.updateMap();
//            System.out.println("Parent size: " + parentSize + " Parent location: " + parent.getLocation());
//            locationMap.put(PortDirection.NORTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
//                                                            parent.getLocation().y - height));
//            locationMap.put(PortDirection.EAST, new Point(parent.getLocation().x + parentSize.x,
//                                                            parent.getLocation().y + parentSize.y/2 - height/2));
//            locationMap.put(PortDirection.SOUTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
//                                                            parent.getLocation().y + parentSize.y));
//            locationMap.put(PortDirection.WEST, new Point(parent.getLocation().x - width,
//                                                            parent.getLocation().y + parentSize.y/2 - height/2));
            this.updateLocation();
        }

        private void updateMap() {
            Point parentSize = parent.getSize();
            locationMap.put(PortDirection.NORTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
                                                            parent.getLocation().y - height));
            locationMap.put(PortDirection.EAST, new Point(parent.getLocation().x + parentSize.x,
                                                            parent.getLocation().y + parentSize.y/2 - height/2));
            locationMap.put(PortDirection.SOUTH, new Point(parent.getLocation().x + parentSize.x/2 - width/2,
                                                            parent.getLocation().y + parentSize.y));
            locationMap.put(PortDirection.WEST, new Point(parent.getLocation().x - width,
                                                            parent.getLocation().y + parentSize.y/2 - height/2));
        }

        @Override
        public void updateLocation(){
            this.updateMap();
            this.location = locationMap.get(direction);
//            System.out.println("Parent location: " + parent.getLocation() + " Port location: " + location);
        }

        @Override
        public Point getLocation() {
            if (direction == PortDirection.NORTH) {
                return new Point(location.x + width / 2, location.y + height);
            } else if (direction == PortDirection.WEST) {
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

        public Base getParent(){
            return parent;
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
            super(ButtonMode.ASSOCIATION.ordinal(), BUTTON_TYPE.LINE);
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
            super(ButtonMode.GENERALIZATION.ordinal(), BUTTON_TYPE.LINE);
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
            super(ButtonMode.COMPOSITION.ordinal(), BUTTON_TYPE.LINE);
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
            this.setGroupType();
        }

        public void setLocation(Point start, Point end){
            this.startLocation = start;
            this.endLocation = end;
        }

        private void setGroupType(){
            for (Base object : groupObjects) {
                object.isGroup = true;
            }
        }

        @Override
        public Point getLocation() {
            return startLocation;
        }

        @Override
        public void draw(Graphics g) {
            super.draw(g);
            if (getSelected() && !isGroup) {
                g.setColor(Color.black);
            }
            else {
                g.setColor(Color.lightGray);
            }
            g.drawRect(startLocation.x, startLocation.y,
                    endLocation.x - startLocation.x, endLocation.y - startLocation.y);

            for (Base object : groupObjects) {
                object.draw(g);
            }
        }

        @Override
        public void move(int dx, int dy) {
//            System.out.println("Group move from " + startLocation + "," + endLocation + " to " +
//                    new Point(startLocation.x + dx, startLocation.y + dy) + ", " + new Point(endLocation.x + dx, endLocation.y + dy));
            this.startLocation.x += dx;
            this.startLocation.y += dy;
            this.endLocation.x += dx;
            this.endLocation.y += dy;
            for (Base object : groupObjects) {
                object.move(dx, dy);
            }
        }

        @Override
        public boolean contains(Point pt) {
            for(Base object: groupObjects) {
                if (object.contains(pt)) {
                    return true;
                }
            }
            return false;
//            return pt.x > startLocation.x && pt.x < endLocation.x &&
//                    pt.y > startLocation.y && pt.y < endLocation.y;

        }

        @Override
        public boolean containInArea(Point start, Point end) {
            return startLocation.x > start.x && endLocation.x < end.x &&
                    startLocation.y > start.y && endLocation.y < end.y;
        }
    }

}


