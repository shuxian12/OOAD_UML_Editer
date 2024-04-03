package components.module.mode;

import components.module.controller.CanvasController;
import components.module.element.UMLObject;

import java.awt.*;

public class SelectMode extends Mode{
    private Point start, end;
    private boolean valid_press = false;
    private MouseBehavior mouseBehavior;
    public SelectMode(CanvasController canvasController) {
        super(canvasController);
        System.out.println("SelectMode initialized");
    }

    @Override
    public void onPressed(Point point) {
        System.out.println("SelectMode onPressed");
        canvasController.unselectALL();
        valid_press = false;
        UMLObject.Base object = this.canvasController.findObject(point);
        if (object != null) {
            valid_press = true;
            this.mouseBehavior = new SelectObject(object);
        } else {
            this.mouseBehavior = new NullSelectBehavior(null);
        }
        this.mouseBehavior.onPressed(point);
    }

    @Override
    public void onDragged(Point point) {
        this.mouseBehavior.onDragged(point);
    }

    @Override
    public void onReleased(Point point) {
        this.mouseBehavior.onReleased(point);
    }

    abstract private class MouseBehavior {
        protected UMLObject.Base target;
        public MouseBehavior(UMLObject.Base target) {
            this.target = target;
        }
        abstract public void onPressed(Point pt);
        abstract public void onDragged(Point pt);
        abstract public void onReleased(Point pt);
    }

    private class NullSelectBehavior extends MouseBehavior {
        // 選取模式
        public NullSelectBehavior(UMLObject.Base target) {
            super(target);
        }
        @Override
        public void onPressed(Point pt) {
            System.out.println("NullSelectBehavior onPressed");
            target = canvasController.createObject(pt);
        }
        @Override
        public void onDragged(Point pt) {
//            System.out.println("NullSelectBehavior onDragged");
            Point prevPt = target.getLocation();
            Point new_point = new Point(Math.min(pt.x, prevPt.x), Math.min(pt.y, prevPt.y));
            Point new_size = new Point(Math.abs(pt.x - prevPt.x), Math.abs(pt.y - prevPt.y));
            this.target.setLocation(new_point);
            this.target.setSize(new_size);
        }
        @Override
        public void onReleased(Point pt) {
            System.out.println("NullSelectBehavior onReleased");

            canvasController.setAreaObjects(this.target.getLocation(), new Point(pt.x, pt.y));

            canvasController.removeObject(this.target);
        }
    }

    private class SelectObject extends MouseBehavior {
        private Point prevPt;
        public SelectObject(UMLObject.Base target) {
            super(target);
        }
        @Override
        public void onPressed(Point pt) {
            System.out.println("SelectObject onPressed");
            target = canvasController.findObject(pt);
            prevPt = pt;
        }
        @Override
        public void onDragged(Point pt) {
//            System.out.println("SelectObject onDragged");
            int dx = pt.x - prevPt.x;
            int dy = pt.y - prevPt.y;
            target.move(dx, dy);
            prevPt = pt;
        }
        @Override
        public void onReleased(Point pt) {
            System.out.println("SelectObject onReleased");
        }
    }
}
