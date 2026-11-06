package teamcode.subsystems;

import ftclib.subsystem.FtcRollerIntake;
import ftclib.motor.FtcMotorActuator.MotorType;
import ftclib.subsystem.FtcShooter;
import teamcode.constants.IntakeConstants;
import teamcode.constants.ShooterConstants;
import trclib.robotcore.TrcEvent;
import trclib.subsystem.TrcSubsystem;

public class Shooter extends TrcSubsystem<Shooter.Action> {

    public enum Action {
        SHOOT,
        IDLE,
        STOP
    }

    private static final String SUBSYSTEM_NAME = "Shooter";

    /** MUST BE SAME AS CONSTANTS!!! */
    private final FtcShooter shooter;

    public Shooter() {
        super(SUBSYSTEM_NAME, false);

        FtcShooter.Params shooterParams =
                new FtcShooter.Params()
                        .setShooterMotor1(
                                ShooterConstants.LEFT_MOTOR_NAME,
                                ShooterConstants.MOTOR_TYPE,
                                ShooterConstants.LEFT_MOTOR_INVERTED,
                                ShooterConstants.MOTOR_VOLTAGE_COMP,
                                ShooterConstants.MOTOR_BRAKE,
                                ShooterConstants.MOTOR_VELOCITY_TRIGGER
                        )
                        .setShooterMotor2(
                                ShooterConstants.RIGHT_MOTOR_NAME,
                                ShooterConstants.MOTOR_TYPE,
                                ShooterConstants.RIGHT_MOTOR_INVERTED,
                                ShooterConstants.MOTOR_VOLTAGE_COMP,
                                ShooterConstants.MOTOR_BRAKE,
                                ShooterConstants.MOTOR_VELOCITY_TRIGGER,
                                ShooterConstants.MOTOR_FOLLOWER
                        );
        shooter = new FtcShooter(
                SUBSYSTEM_NAME,
                shooterParams
        );
    }

    /* Motor Methods */
    public void stop() {
        shooter.getShooter().stopShooter();
    }

    public void shoot() {
        spinShooter(ShooterConstants.SHOOTING_SPEED);
    }

    public void idle() {
        spinShooter(ShooterConstants.IDLE_SPEED);
    }

    /** Rev/Sec */
    private void spinShooter(double speed) {
        shooter.getShooter().setShooterMotorVelocity(speed, speed);
    }

    /* TrcSubsystem Methods */
    @Override
    public void cancel() {
        stop();
    }

    @Override
    public void zeroCalibrate(String owner, TrcEvent completionEvent) {
    }

    @Override
    public void resetState() {
        stop();
    }

    @Override
    public void subsystemControl(boolean altFunc, double... inputs) {
    }

    @Override
    public void subsystemAction(Action action, Object context) {
        if (action == null) {
            return;
        }

        switch (action) {
            case SHOOT:
                shoot();
                break;
            case IDLE:
                idle();
                break;
            case STOP:
                stop();
                break;
        }
    }

    @Override
    public void tuneSubsystem(TuneAction action, String tuneSubsystemName) {
    }

    @Override
    public void publishToDashboard() {
    }

    @Override
    public int updateStatus(int lineNum, boolean slowLoop) {
        return lineNum;
    }

    @Override
    public void updateParamsToDashboard(String subsystemName) {
    }

    @Override
    public void updateParamsFromDashboard(String subsystemName) {
    }
}