package utils.config;

import components.module.element.BaseObject;
import components.module.element.line.AssociationLine;
import components.module.element.line.CompositionLine;
import components.module.element.line.GeneralizationLine;
import components.module.element.shape.ClassObject;
import components.module.element.shape.SelectSquare;
import components.module.element.shape.UseCaseObject;

import java.awt.*;

public enum ObjectType {
    SELECT, ASSOCIATION, GENERALIZATION, COMPOSITION, CCLASS, USECASE, PORT, GROUP, SELECT_SQUARE;

    public static ObjectType getType(int mode) {
        return ObjectType.values()[mode];
    }

    public BaseObject getConstructor(Point point) {
        switch (this) {
            case CCLASS:
                return new ClassObject(point);
            case USECASE:
                return new UseCaseObject(point);
            case SELECT:
                return new SelectSquare(point);
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
}
