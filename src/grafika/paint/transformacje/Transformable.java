package grafika.paint.transformacje;

import grafika.paint.figury.DoublePoint;
import grafika.paint.figury.Point;

public interface Transformable {
    public void translate(Point vector);
    public void rotate(Point centerPoint, double angle);
    public void scale(Point centerPoint, DoublePoint vector);
}
