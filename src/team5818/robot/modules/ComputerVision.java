package team5818.robot.modules;

import org.usfirst.frc.team5818.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import team5818.robot.RobotConstants;
import team5818.robot.modules.USBCam;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Handles camera transmission and light managing for the camera.
 */
public class ComputerVision {

    // TODO:switch for actual robot
    private final int BRIGHTNESS_DEFAULT = 10;
    private final int EXPOSURE_DEFAULT = 50;

    /**
     * The driving camera that is directed with the direction we collect.
     * 
     */
    public static int CAMERA_DRIVER = 4;

    /**
     * The shooter Camera that faces with the flywheel.
     *
     */
    public static int CAMERA_SHOOTER = 3;
    public static int CAMERA_BACK = 5;

    private USBCam camDriver;
    private USBCam camShooter;
    private USBCam camBack;
    private USBCam currcam;
    private Image frame;
    private Solenoid LEDLight;

    /**
     * Initializes the light ring and the cameras. Begins the capture.
     */
    public ComputerVision() {

        // Set up camDriver
        try {
            camDriver = new USBCam("cam" + CAMERA_DRIVER);

            if (camDriver != null) {
                //camDriver.setSize(320, 240);
                //camDriver.setFPS(20);
                //camDriver.updateSettings();
                //camDriver.openCamera();
                camDriver.setSize(160, 120);
                camDriver.setFPS(30);
                camDriver.setBrightness(BRIGHTNESS_DEFAULT);
                // \/ is the problem
                camDriver.setExposureManual(EXPOSURE_DEFAULT * 2);
                camDriver.updateSettings();
                camDriver.openCamera();
            }
        } catch (Exception e) {
            camDriver = null;
            DriverStation.reportError("camDriver is not attached.\n", false);
        }

        // Set up camShooter
        try {
            camShooter = new USBCam("cam" + CAMERA_SHOOTER);

            if (camShooter != null) {
                camShooter.setSize(320, 240);
                camShooter.setFPS(30);
                camShooter.setBrightness(BRIGHTNESS_DEFAULT);
                // \/ is the problem
                camShooter.setExposureManual(EXPOSURE_DEFAULT);
                camShooter.updateSettings();
                camShooter.openCamera();
            }
        } catch (Exception e) {
            camShooter = null;
            DriverStation.reportError("camShooter is not attached.\n", false);
        }

        try {
            camBack = new USBCam("cam" + CAMERA_BACK);

            if (camBack != null) {
                camBack.setSize(640, 360);
                camBack.setFPS(30);
                camBack.updateSettings();
                camBack.openCamera();
            }
        } catch (Exception e) {
            camBack = null;
            DriverStation.reportError("camBack is not attached.\n", false);
        }

        // Set Frame, Current Camera, and Restart Capture
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        currcam = camShooter;
        // TODO change to Camera_Driver when done working on vision
        this.ChangeFeed(CAMERA_SHOOTER);
        currcam.startCapture();
    }

    /**
     * Starts the camera feed transmission to dashboard.
     */
    public synchronized void runCV() {
        currcam.getImage(frame);
        CameraServer.getInstance().setImage(frame);
    }

    /**
     * Stops the camera feed transmission to dashboard.
     */
    public synchronized void EndCV() {
        if (currcam == camDriver)
            camDriver.stopCapture();
        else
            camShooter.stopCapture();
    }

    /**
     * Switches the camera feed to the specified value. See public Constants in
     * ComputerVision.
     * 
     * @param i
     *            The desired camera number.
     */
    public synchronized void ChangeFeed(int i) throws NullPointerException{
        try {
            // TODO:switch for actual robot
            if (i == CAMERA_SHOOTER && camShooter != null) {
                if (camShooter != null) {

                    camDriver.stopCapture();
                    
                    // Toggle Auto Exposure and brightness according to
                    // preferences
                    boolean AutoPref = Preferences.getInstance()
                            .getBoolean("ShooterCamAutoPref", false);

                    if (AutoPref == false) {
                        // Set Camera Settings
                        camShooter.setBrightness(BRIGHTNESS_DEFAULT);
                        camShooter.setExposureManual(EXPOSURE_DEFAULT);
                        camShooter.updateSettings();
                    }
                    currcam = camShooter;
                    camShooter.startCapture();
                }
            }
        } catch (Exception e) {
            if (e.getMessage() != null)
                DriverStation.reportError("Could Not Switch Camera Shooter", false);
        }
        try {
            if (i == CAMERA_DRIVER && camDriver != null) {
                if (camShooter != null) {
                    camShooter.stopCapture();
                    currcam = camDriver;
                    camDriver.startCapture();
                }
            }
        } catch (Exception e) {
            if (e.getMessage() != null)
                DriverStation.reportError("Could Not Switch Camera Driver", false);
        }
        try {
            if (i == CAMERA_BACK && camBack != null) {
                if (camBack != null) {
                    camBack.stopCapture();
                    currcam = camBack;
                    camBack.startCapture();
                }
            }
        } catch (Exception e) {
            // DriverStation.reportError("Camera Feed Switching Error\n",
            // false);
            if (e.getMessage() != null)
                DriverStation.reportError("Could Not Switch Camera Back", false);
        }
    }

    /**
     * Switches the state of the LED
     * 
     * @param toggle
     *            Whether to run the light.
     */
    public void LEDToggle(Boolean toggle) throws NullPointerException {
        LEDLight.set(toggle);
    }

}