package org.usfirst.frc.team5818.robot.modules.drivetrain;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * A drive side is an arbitrary amount of talons that can be manipulated as a
 * whole set. The arbitrary amount of talons that can be manipulated may be an
 * integer between 1 and 2 inclusive.
 */
public class DriveSide implements PIDOutput {

    private final CANTalon mainTalon;
    private final CANTalon secondaryTalon;
    private final CANTalon thirdTalon;
    private final boolean inverted;
    private static final double powerLimit = 0.5;
    private static final boolean cubeCurve = true;
    

    /**
     * Creates a new DriveSide that controls the talons given.
     * 
     * @param mainTalon
     *            - The first talon to control
     * @param secondaryTalon
     *            - The second talon to control
     */
    public DriveSide(CANTalon mainTalon, CANTalon secondaryTalon, CANTalon thirdTalon) {
        this(mainTalon, secondaryTalon, thirdTalon, false);
    }

    /**
     * Creates a new DriveSide that controls the talons given, and may be
     * inverted.
     * 
     * @param mainTalon
     *            - The first talon to control
     * @param secondaryTalon
     *            - The second talon to control
     * @param secondaryTalon
     *            - The third talon to control
     * @param inverted
     *            - {@code true} if the argument of {@link #pidWrite(double)}
     *            should be negated
     */
    public DriveSide(CANTalon mainTalon, CANTalon secondaryTalon, CANTalon thirdTalon,
            boolean inverted) {
        if (mainTalon == null) {
            throw new IllegalArgumentException("mainTalon cannot be null");
        }
        this.mainTalon = mainTalon;
        this.secondaryTalon = secondaryTalon;
        this.thirdTalon = thirdTalon;
        this.inverted = inverted;
    }

    @Override
    public void pidWrite(double output) {
        if (inverted) {
            output *= -1;
        }
        
        if(cubeCurve)
            output = output * output * output;
        
        if(output > powerLimit)
            output = powerLimit;
        if(output < -powerLimit)
            output = -powerLimit;
        
        this.mainTalon.set(output);
        if (this.secondaryTalon != null) {
            this.secondaryTalon.set(output);
        }
        if (this.thirdTalon != null) {
            this.thirdTalon.set(output);
        }
    }

}
