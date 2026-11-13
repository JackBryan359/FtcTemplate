package teamcode.constants;

import com.qualcomm.robotcore.hardware.configuration.annotations.MotorType;

import ftclib.motor.FtcDcMotor;
import ftclib.motor.FtcMotorActuator;

public class IntakeConstants {
    /** The name your motor is given. */
    public static final String MOTOR_NAME = "IntakeMotor";
    /** The kind of motor your motor is. The BILDA 5203 Series Yellow Jacket is a DcMotor. */
    public static final FtcMotorActuator.MotorType MOTOR_TYPE = FtcMotorActuator.MotorType.DcMotor;
    /** The inverted value of your motor (clockwise/counter-clockwise) */
    public static final boolean MOTOR_INVERTED = true;
    /** The voltage compensation of the motor. If enabled, it attempts to combat voltage drop in the battery.
     * Ex: If the motor runs at 50% while the battery is at 12v, it runs at 6v. However, when battery is at 10v,
     * the motor will run at 5v. This voltage compensation attempts to combat that and keep a consistent speed.*/
    public static final boolean MOTOR_VOLTAGE_COMP = false;
    /** The brake mode of the motor. If enabled, the motor is on brake, and attempts to stay in place.
     * If on coast (brake mode = false), the motor will freely spin without resistance. */
    public static final boolean MOTOR_BRAKE = true;
    /** The speed of the motor while doing its action. 0.5 is 50% speed.*/
    public static final double INTAKE_SPEED = 1.0;
    public static final double EJECT_SPEED = -1.0;
    /** The speed of the motor whilst not doing the other actions. Basically, the motor at rest.*/
    public static final double IDLE_SPEED = 0.0;
}