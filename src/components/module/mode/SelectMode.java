package components.module.mode;

import components.module.controller.CanvasController;
import components.module.element.BaseObject;
import components.module.element.UMLObject;

import java.awt.*;

public class SelectMode extends Mode{
    private MouseBehavior mouseBehavior;
    public SelectMode(CanvasController canvasController, int mode) {
        super(canvasController, mode);
        System.out.println("SelectMode initialized");
    }

    @Override
    public void onPressed(Point point) {
        System.out.println("SelectMode onPressed");
        canvasController.unselectALL();
        BaseObject object = this.canvasController.findObject(point);
        if (object != null) {
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
        protected BaseObject target;
        public MouseBehavior(BaseObject target) {
            this.target = target;
        }
        abstract public void onPressed(Point pt);
        abstract public void onDragged(Point pt);
        abstract public void onReleased(Point pt);
    }

    private class NullSelectBehavior extends MouseBehavior {
        private Point initialPoint;
        // 選取模式
        public NullSelectBehavior(BaseObject target) {
            super(target);
        }
        @Override
        public void onPressed(Point pt) {
            System.out.println("NullSelectBehavior onPressed");
            this.target = canvasController.createObject(pt);
            this.initialPoint = pt;
        }
        @Override
        public void onDragged(Point pt) {
//            System.out.println("NullSelectBehavior onDragged");
            Point newPoint = new Point(Math.min(pt.x, initialPoint.x), Math.min(pt.y, initialPoint.y));
            Point newSize = new Point(Math.abs(pt.x - initialPoint.x), Math.abs(pt.y - initialPoint.y));
            this.target.setLocation(newPoint);
            this.target.setSize(newSize);
        }
        @Override
        public void onReleased(Point pt) {
            System.out.println("NullSelectBehavior onReleased");

            canvasController.setAreaObjects(new Point(Math.min(pt.x, initialPoint.x), Math.min(pt.y, initialPoint.y)),
                    new Point(Math.max(pt.x, initialPoint.x), Math.max(pt.y, initialPoint.y)));

            canvasController.removeObject(this.target);
        }
    }

    private class SelectObject extends MouseBehavior {
        private Point prevPt;
        public SelectObject(BaseObject target) {
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
