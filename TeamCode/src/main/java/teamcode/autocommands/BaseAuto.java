
package teamcode.autocommands;

import teamcode.Robot;
import teamcode.FtcAuto.AutoChoices;
import trclib.robotcore.TrcEvent;
import trclib.robotcore.TrcRobot;
import trclib.robotcore.TrcStateMachine;
import trclib.timer.TrcTimer;

public class BaseAuto implements TrcRobot.RobotCommand {

    private enum State {
        START,
        DRIVE_FORWARD,
        SHOOT,
        STRAFE_RIGHT,
        DONE
    }

    private final Robot robot;
    private final AutoChoices autoChoices;

    private final TrcStateMachine<State> sm;
    private final TrcTimer timer;
    private final TrcEvent timerEvent;
    private final TrcEvent driveEvent;

    public BaseAuto(Robot robot, AutoChoices autoChoices) {
        this.robot = robot;
        this.autoChoices = autoChoices;

        sm = new TrcStateMachine<>("CmdLeftAuto");
        timer = new TrcTimer("CmdLeftAuto");
        timerEvent = new TrcEvent("TimerEvent");
        driveEvent = new TrcEvent("DriveEvent");
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
                sm.waitForEvents(State.SHOOT, driveEvent);
                break;

            case SHOOT:
//                robot.shooter.shoot();
//                robot.indexer.intake();

                timer.set(3.0, timerEvent);
                sm.waitForEvents(State.STRAFE_RIGHT, timerEvent);
                break;

            case STRAFE_RIGHT:
                robot.shooter.stop();
                robot.indexer.stop();

                robot.robotBase.driveBase.holonomicDrive(
                        0.4, 0.0, 0.0, 1.0, driveEvent);
                sm.waitForEvents(State.DONE, driveEvent);
                break;

            case DONE:
                cancel();
                break;
        }

        return !sm.isEnabled();
    }
}
