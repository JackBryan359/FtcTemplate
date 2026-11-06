package teamcode.subsystems;

import ftclib.subsystem.FtcRollerIntake;
import ftclib.motor.FtcMotorActuator.MotorType;
import teamcode.constants.IntakeConstants;
import trclib.robotcore.TrcEvent;
import trclib.subsystem.TrcSubsystem;

public class Intake extends TrcSubsystem<Intake.Action> {

    public enum Action {
        INTAKE,
        EJECT,
        STOP
    }

    private static final String SUBSYSTEM_NAME = "Intake";

    private final FtcRollerIntake intake;

    public Intake() {
        super(SUBSYSTEM_NAME, false);

        FtcRollerIntake.Params intakeParams =
                new FtcRollerIntake.Params()
                        .setPrimaryMotor(
                                IntakeConstants.MOTOR_NAME,
                                IntakeConstants.MOTOR_TYPE,
                                IntakeConstants.MOTOR_INVERTED,
                                IntakeConstants.MOTOR_VOLTAGE_COMP,
                                IntakeConstants.MOTOR_BRAKE
                        )
                        .setPowerLevels(
                                IntakeConstants.INTAKE_SPEED,
                                IntakeConstants.EJECT_SPEED,
                                IntakeConstants.IDLE_SPEED
                        );

        intake = new FtcRollerIntake(
                SUBSYSTEM_NAME,
                intakeParams
        );
    }

    /* Motor Methods */
    public void intake() {
        intake.getIntake().intake();
    }

    public void intake(double percentOutput) {
        intake.getIntake().intake(percentOutput);
    }

    public void eject() {
        intake.getIntake().eject();
    }

    public void eject(double percentOutput) {
        intake.getIntake().eject(percentOutput);
    }

    public void stop() {
        intake.getIntake().stop();
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
            case INTAKE:
                intake();
                break;
            case EJECT:
                eject();
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