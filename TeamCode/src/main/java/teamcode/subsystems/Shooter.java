package teamcode.subsystems;

import ftclib.subsystem.FtcShooter;
import teamcode.constants.ShooterConstants;
import trclib.motor.TrcMotor;
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
                                ShooterConstants.MOTOR_NAME,
                                ShooterConstants.MOTOR_TYPE,
                                ShooterConstants.MOTOR_INVERTED,
                                ShooterConstants.MOTOR_VOLTAGE_COMP,
                                ShooterConstants.MOTOR_BRAKE,
                                ShooterConstants.MOTOR_VELOCITY_TRIGGER
                        );
        shooter = new FtcShooter(
                SUBSYSTEM_NAME,
                shooterParams
        );

        shooter.getShooter().getShooterMotor1().setVelocityPidParameters(
                new TrcMotor.PidParams()
                        .setPidCoefficients(
                                ShooterConstants.kP,
                                ShooterConstants.kI,
                                ShooterConstants.kD,
                                ShooterConstants.kF
                        )
                        .setPidControlParams(
                                ShooterConstants.VELOCITY_TOLERANCE,
                                0.0,
                                false
                        ),
                null
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

    private boolean atSpeed(double speed, double range) {
        return (shooter.getShooter().getShooterMotor1Power() > (speed-range))
                && (shooter.getShooter().getShooterMotor1Power() < (speed+range));
    }

    public boolean atShootingSpeed() {
        return atSpeed(ShooterConstants.SHOOTING_SPEED, 0.05);
    }

    /** Rev/Sec */
    private void spinShooter(double speed) {
        shooter.getShooter().getShooterMotor1().setPower(speed);
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