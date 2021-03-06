package team5818.robot.modules.drivetrain;

import team5818.robot.util.Vector2d;

/**
 * A {@link DriveCalculator} that computes values for arcade drive.
 */
public enum ArcadeDriveCalculator implements DriveCalculator {
    /**
     * The only instance of this calculator.
     */
    INSTANCE;

    public static final int JOYSTICK_MODE_CURVE = 1;
    public static final int JOYSTICK_MODE_REGULAR = 0;

    private static int joystickMode = JOYSTICK_MODE_CURVE;
    private static double turnMult = 1;
    private static double turnPower = 1.5;

    private static double forwardMult = 1;
    private static double forwardPower = 1;

    @Override
    public Vector2d compute(Vector2d leftAndRight) {
        if (joystickMode == JOYSTICK_MODE_CURVE)
            return computeTurnsDifferent(leftAndRight);
        else
            return computeDefault(leftAndRight);
    }

    public Vector2d computeDefault(Vector2d in) {
        double rotateValue = -in.getX();
        double moveValue = in.getY();
        double leftMotorSpeed;
        double rightMotorSpeed;
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        return new Vector2d(leftMotorSpeed, rightMotorSpeed);
    }

    public Vector2d computeTurnsDifferent(Vector2d in) {
        double rotateValue = Math.signum(-in.getX())
                * Math.pow(Math.abs(in.getX()), turnPower) * turnMult; // Less
        double moveValue = Math.signum(-in.getY())
                * Math.pow(Math.abs(in.getY()), forwardPower) * forwardMult; // Less

        double leftMotorSpeed;
        double rightMotorSpeed;
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        return new Vector2d(leftMotorSpeed, rightMotorSpeed);
    }

    public int getJoystickMode(int mode) {
        return joystickMode;
    }

}
