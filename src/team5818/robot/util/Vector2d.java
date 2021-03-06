package team5818.robot.util;

/**
 * A simple 2D vector class.
 */
public class Vector2d {

    private final double x;
    private final double y;

    /**
     * Creates a new vector from an x and y.
     * 
     * @param x
     *            - x value
     * @param y
     *            - y value
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value.
     * 
     * @return The x value.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y value.
     * 
     * @return The y value
     */
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", x, y);
    }
    // TODO: more vector math!

}
