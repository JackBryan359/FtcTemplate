package teamcode.autocommands;

import teamcode.Robot;
import teamcode.FtcAuto.AutoChoices;
import teamcode.subsystems.Indexer;
import teamcode.subsystems.Shooter;
import trclib.robotcore.TrcEvent;
import trclib.robotcore.TrcRobot;
import trclib.robotcore.TrcStateMachine;
import trclib.timer.TrcTimer;

public class DriveForwardAuto implements TrcRobot.RobotCommand {

    private enum State {
        START,
        DRIVE_FORWARD,
        DONE
    }

    private final Robot robot;
    private final AutoChoices autoChoices;

    private final TrcStateMachine<State> sm;
    private final TrcTimer timer;
    private final TrcEvent timerEvent;
    private final TrcEvent driveEvent;
    private int shootStep;

    public DriveForwardAuto(Robot robot, AutoChoices autoChoices) {
        this.robot = robot;
        this.autoChoices = autoChoices;

        sm = new TrcStateMachine<>("CmdLeftAuto");
        timer = new TrcTimer("CmdLeftAuto");
        timerEvent = new TrcEvent("TimerEvent");
        driveEvent = new TrcEvent("DriveEvent");

        shootStep = 0;
    }

    @Override
    public void start() {
        sm.start(State.START);
    }

    @Override
    public void cancel() {
        timer.cancel();
        robot.robotBase.driveBase.stop();
        robot.shooter.stop();
        robot.indexer.stop();
        robot.intake.stop();
        robot.shooter.stop();
        sm.stop();
    }

    @Override
    public boolean isActive() {
        return sm.isEnabled();
    }

    @Override
    public boolean cmdPeriodic(double elapsedTime) {

        State state = sm.checkReadyAndGetState();

        if (state == null) {
            return !sm.isEnabled();
        }

        switch (state) {

            case START:
                robot.setRobotStartPosition(autoChoices);
                sm.setState(State.DRIVE_FORWARD);
                break;

            case DRIVE_FORWARD:
                robot.robotBase.driveBase.holonomicDrive(
                        0.0, 0.4, 0.0, 1.0, driveEvent);
                sm.waitForEvents(State.DONE, driveEvent);
                break;

            case DONE:
                cancel();
                break;
        }

        return !sm.isEnabled();
    }
}
