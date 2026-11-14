package teamcode.autocommands;

import teamcode.Robot;
import teamcode.FtcAuto.AutoChoices;
import teamcode.subsystems.Indexer;
import teamcode.subsystems.Shooter;
import trclib.robotcore.TrcEvent;
import trclib.robotcore.TrcRobot;
import trclib.robotcore.TrcStateMachine;
import trclib.timer.TrcTimer;

    public class BaseAuto implements TrcRobot.RobotCommand {

        private enum State {
            START,
            SPIN_UP,
            SHOOT,
            DRIVE_FORWARD,
            DRIVE_RIGHT_TO_PARK,
            DONE
        }

        private final Robot robot;
        private final AutoChoices autoChoices;

        private final TrcStateMachine<State> sm;
        private final TrcTimer timer;
        private final TrcEvent timerEvent;
        private final TrcEvent driveEvent;
        private int shootStep;

        public BaseAuto(Robot robot, AutoChoices autoChoices) {
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
                    sm.setState(State.SPIN_UP);
                    break;

                case SPIN_UP:
                    robot.shooter.subsystemAction(Shooter.Action.SHOOT, null);
                    timer.set(3.0, timerEvent);
                    sm.waitForEvents(State.SHOOT, timerEvent);
                    break;

                case SHOOT:
                    switch (shootStep) {

                        case 0:
                            // Start shooter and indexer
                            robot.indexer.subsystemAction(Indexer.Action.INTAKE, null);

                            shootStep = 1;
                            timer.set(0.5, timerEvent);
                            sm.waitForEvents(State.SHOOT, timerEvent);
                            break;

                        case 1:
                            // Stop indexer, keep shooter running
                            robot.indexer.subsystemAction(Indexer.Action.STOP, null);

                            shootStep = 2;
                            timer.set(0.5, timerEvent);
                            sm.waitForEvents(State.SHOOT, timerEvent);
                            break;

                        case 2:
                            // Index again
                            robot.indexer.subsystemAction(Indexer.Action.INTAKE, null);

                            shootStep = 3;
                            timer.set(0.5, timerEvent);
                            sm.waitForEvents(State.SHOOT, timerEvent);
                            break;

                        case 3:
                            // Stop indexer and finish shooting
                            robot.indexer.subsystemAction(Indexer.Action.STOP, null);

                            shootStep = 4;
                            timer.set(0.5, timerEvent);
                            sm.waitForEvents(State.SHOOT, timerEvent);
                            break;

                        case 4:
                            // Index again
                            robot.indexer.subsystemAction(Indexer.Action.INTAKE, null);

                            shootStep = 5;
                            timer.set(0.5, timerEvent);
                            sm.waitForEvents(State.SHOOT, timerEvent);
                            break;

                        case 5:
                            // Stop indexer and finish shooting
                            robot.indexer.subsystemAction(Indexer.Action.STOP, null);

                            shootStep = 6;
                            timer.set(0.5, timerEvent);
                            sm.waitForEvents(State.SHOOT, timerEvent);
                            break;

                        case 6:
                            // Index again
                            robot.indexer.subsystemAction(Indexer.Action.INTAKE, null);

                            shootStep = 7;
                            timer.set(0.5, timerEvent);
                            sm.waitForEvents(State.SHOOT, timerEvent);
                            break;

                        case 7:
                            // Stop indexer and finish shooting
                            robot.indexer.subsystemAction(Indexer.Action.STOP, null);
                            robot.shooter.subsystemAction(Shooter.Action.STOP, null);

                            shootStep = 0;
                            sm.setState(State.DRIVE_FORWARD);
                            break;
                    }
                    break;

                case DRIVE_FORWARD:
                    robot.robotBase.driveBase.holonomicDrive(
                            0.0, 0.4, 0.0, 1.0, driveEvent);
                    sm.waitForEvents(State.DRIVE_RIGHT_TO_PARK, driveEvent);
                    break;

                case DRIVE_RIGHT_TO_PARK:
                    robot.robotBase.driveBase.holonomicDrive(
                            0.4, 0.0, 0.0, 2.0, driveEvent);
                    sm.waitForEvents(State.DONE, driveEvent);
                    break;

                case DONE:
                    cancel();
                    break;
            }

            return !sm.isEnabled();
        }
    }
