package team5818.robot.commands;

import org.usfirst.frc.team5818.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import team5818.robot.modules.drivetrain.DriveTrain;
import team5818.robot.util.Vector2d;

public class DrivePower extends Command{
    
    private DriveTrain train = Robot.runningRobot.driveTrain;
    private double drivePower;
    private Vector2d powerVec;

    public DrivePower(Vector2d powerVec) {
        this.powerVec = powerVec;
        setTimeout(0);
        requires(Robot.runningRobot.driveTrain);
    }
    public DrivePower(double power, double timeout){
        powerVec = new Vector2d(drivePower, drivePower);
        setTimeout(timeout);
        requires(Robot.runningRobot.driveTrain);
    }
    
    @Override
    protected void initialize() {
        train.setPower(powerVec);
        
    }

    @Override
    protected void execute() {
        
    }

    @Override
    protected boolean isFinished() {
        return true;

    }

    @Override
    protected void end() {
        Vector2d stopVec = new Vector2d(0,0);
        train.setPower(stopVec);       
    }

    @Override
    protected void interrupted() {
        end();
        
    }

}
