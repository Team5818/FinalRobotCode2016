package org.usfirst.frc.team5818.robot.modules.drivetrain;

import org.usfirst.frc.team5818.robot.RobotConstants;
import org.usfirst.frc.team5818.robot.util.ConstantPIDSource;
import org.usfirst.frc.team5818.robot.util.MathUtil;
import org.usfirst.frc.team5818.robot.util.Vector2d;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * Wraps around {@link DriveTrain} to help drive certain distances and control
 * different ways of driving.
 */
public class DriveTrainController {

    private static final double ROBOT_WIDTH_IN_FEET = 2.3;

    private final DriveTrain driveTrain;
    private DriveCalculator driveCalculator;

    public DriveTrainController(DriveTrain driveTrain,
            DriveCalculator driveCalculator) {
        this.driveTrain = driveTrain;
        setDriveCalculator(driveCalculator);
    }

    public void setDriveCalculator(DriveCalculator driveCalculator) {
        if (driveCalculator == null) {
            throw new NullPointerException("fix your code genius.");
        }
        this.driveCalculator = driveCalculator;
    }

    public void driveToTargetXFeetAway(double feet) {
        driveTrain.getLeftMotors().setDriveDistance(feet);
        driveTrain.getRightMotors().setDriveDistance(feet);
    }

    public void setPowerDirectly(Vector2d power) {
        driveTrain.setPower(power);
    }

    public void recalculateAndSetPower(Vector2d joystickData) {
        setPowerDirectly(driveCalculator.compute(joystickData));
    }

    public void rotateDegrees(double degrees, boolean clockwise) {
        // Force degrees to 0-360.
        degrees %= 360d;
        if (degrees < 0) {
            // Already modulo-360, so this will get us positive
            degrees += 360;
        }
        if (!clockwise) {
            // Invert on clockwise.
            degrees *= -1;
        }
        // degrees is now a normalized value between -360 and 360.
        rotateNormalizedDegrees(degrees);
    }

    private void rotateNormalizedDegrees(double degrees) {
        // To rotate X degrees, simply move left side forward by W
        // and move right side backwards by W
        double distance = MathUtil.distanceOfArc(ROBOT_WIDTH_IN_FEET, degrees);
        driveTrain.getLeftMotors().setDriveDistance(distance);
        driveTrain.getRightMotors().setDriveDistance(-distance);
        ConstantPIDSource left = new ConstantPIDSource(0.1);
        ConstantPIDSource right = new ConstantPIDSource(-0.1);
        driveTrain.getLeftMotors().createPIDLoop(left);
        driveTrain.getRightMotors().createPIDLoop(right);
    }

}
