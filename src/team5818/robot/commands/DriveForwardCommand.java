package team5818.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import team5818.robot.RobotCommon;
import team5818.robot.util.Vector2d;

public class DriveForwardCommand extends Command {

    private boolean hasStarted;
    private boolean hasRun;

    @Override
    public synchronized void start() {
        RobotCommon.runningRobot.driveTrain.setPower(new Vector2d(0, 1));
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        hasStarted = true;
        hasRun = true;
    }

    @Override
    protected boolean isFinished() {
        return hasStarted && hasRun;
    }

    @Override
    public synchronized void cancel() {
        end();
    }

    @Override
    protected void end() {
        RobotCommon.runningRobot.driveTrain.setPower(new Vector2d(0, 0));
    }

    @Override
    protected void interrupted() {
        end();
    }

}
