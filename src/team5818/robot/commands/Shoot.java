package team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import team5818.robot.modules.Collector;
import team5818.robot.modules.ComputerVision;

public class Shoot extends CommandGroup {

    private SetFlywheelPower flyToZero = new SetFlywheelPower(0);
    private Collect collectIn = new Collect(Collect.COLLECT_POWER, 2);

    private SwitchFeed switchCam = new SwitchFeed(ComputerVision.CAMERA_SHOOTER);
    private SwitchFeed switchBack = new SwitchFeed(ComputerVision.CAMERA_DRIVER);


    /**
     * The maximum time the shooter can be on in nano seconds.
     */
    private double maxShootTime = 4;

    /**
     * @param angle
     *            to raise arm to
     * @param flyUpVel
     *            velocity to spin upper fly to
     * @param flyLoVel
     *            velocity to spin lower fly to
     */
    public Shoot() {
        this.addSequential(switchCam);
        this.addSequential(collectIn);
        this.addSequential(flyToZero);
        this.addSequential(switchBack);
    }

}
