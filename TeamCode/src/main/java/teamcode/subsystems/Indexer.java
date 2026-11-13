package teamcode.subsystems;

import ftclib.subsystem.FtcRollerIntake;
import ftclib.motor.FtcMotorActuator.MotorType;
import teamcode.constants.IndexerConstants;
import teamcode.constants.IntakeConstants;
import trclib.robotcore.TrcEvent;
import trclib.subsystem.TrcSubsystem;

public class Indexer extends TrcSubsystem<Indexer.Action> {

    public enum Action {
        INTAKE,
        EJECT,
        STOP
    }

    private static final String SUBSYSTEM_NAME = "Indexer";

    private final FtcRollerIntake indexer;

    public Indexer() {
        super(SUBSYSTEM_NAME, false);

        FtcRollerIntake.Params indexerParams =
                new FtcRollerIntake.Params()
                        .setPrimaryMotor(
                                IndexerConstants.MOTOR_NAME,
                                IndexerConstants.MOTOR_TYPE,
                                IndexerConstants.MOTOR_INVERTED,
                                IndexerConstants.MOTOR_VOLTAGE_COMP,
                                IndexerConstants.MOTOR_BRAKE
                        );

        indexer = new FtcRollerIntake(
                SUBSYSTEM_NAME,
                indexerParams
        );
    }

    /* Motor Methods */
    public void intake() {
        intake(IntakeConstants.INTAKE_SPEED);
    }

    public void intake(double percentOutput) {
        indexer.getIntake().intake(percentOutput);
    }

    public void eject() {
        eject(IntakeConstants.EJECT_SPEED);
    }

    public void eject(double percentOutput) {
        indexer.getIntake().eject(percentOutput);
    }

    public void stop() {
        indexer.getIntake().stop();
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