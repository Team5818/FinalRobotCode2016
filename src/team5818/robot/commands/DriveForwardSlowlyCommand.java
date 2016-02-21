package team5818.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import team5818.robot.RobotCommon;

public class DriveForwardSlowlyCommand extends Command {

    private boolean hasStarted;
    private boolean hasRun;

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        hasStarted = true;
        RobotCommon.runningRobot.driveTrainController
                .driveToTargetXInchesAway(5);
        hasRun = true;
    }

    @Override
    protected boolean isFinished() {
        return hasStarted && hasRun;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
