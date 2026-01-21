package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class RobocatsThree extends OpMode {

    // Drive
    private DcMotor driveLeft;
    private DcMotor driveRight;

    // Shooter
    private DcMotor shooterLeft;
    private DcMotor shooterRight;

    // Intake
    private DcMotor intake;

    // Intake wheels (goBILDA Dual Mode Torque Servos)
    private Servo intakeWheelLeft;
    private Servo intakeWheelRight;

    // Lift
    private DcMotor lift;

    // Feeder (goBILDA Dual Mode Servo)
    private Servo feeder;

    // Power constants
    private final double SHOOTER_POWER = 1.0;
    private final double INTAKE_POWER = -0.5;
    private final double LIFT_POWER = 1.0;

    // CR Servo positions
    private final double SERVO_STOP = 0.5;

    // Intake wheel directions (opposite)
    private final double INTAKE_WHEEL_LEFT_FORWARD = 1.0;
    private final double INTAKE_WHEEL_RIGHT_FORWARD = 0.0;

    // Feeder positions
    private final double FEEDER_FORWARD = 1.0;

    // Toggle states
    private boolean feederOn = false;
    private boolean shootersOn = false;
    private boolean intakeOn = false;
    private boolean liftRunning = false;

    // Edge detection
    private boolean prevY = false;
    private boolean prevA = false;
    private boolean prevX = false;
    private boolean prevDpadUp = false;
    private boolean prevDpadDown = false;

    @Override
    public void init() {

        // Map hardware
        driveLeft = hardwareMap.get(DcMotor.class, "driveLeft");
        driveRight = hardwareMap.get(DcMotor.class, "driveRight");

        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");

        intake = hardwareMap.get(DcMotor.class, "intake");

        intakeWheelLeft = hardwareMap.get(Servo.class, "intakeWheelLeft");
        intakeWheelRight = hardwareMap.get(Servo.class, "intakeWheelRight");

        lift = hardwareMap.get(DcMotor.class, "lift");

        feeder = hardwareMap.get(Servo.class, "feeder");

        // Motor directions
        driveLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveRight.setDirection(DcMotorSimple.Direction.FORWARD);

        shooterLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterRight.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        // Stop servos on init
        intakeWheelLeft.setPosition(SERVO_STOP);
        intakeWheelRight.setPosition(SERVO_STOP);
        feeder.setPosition(SERVO_STOP);

        telemetry.addLine("Initialized – Intake wheels are Dual Mode Servos");
        telemetry.update();
    }

    @Override
    public void loop() {

        // ---------------------------
        // FEEDER TOGGLE (gamepad2.y)
        // ---------------------------
        boolean y = gamepad2.y;
        if (y && !prevY) {
            feederOn = !feederOn;
            feeder.setPosition(feederOn ? FEEDER_FORWARD : SERVO_STOP);
        }
        prevY = y;

        // ---------------------------
        // SHOOTER TOGGLE (gamepad2.a)
        // ---------------------------
        boolean a = gamepad2.a;
        if (a && !prevA) {
            shootersOn = !shootersOn;
            double power = shootersOn ? SHOOTER_POWER : 0.0;
            shooterLeft.setPower(power);
            shooterRight.setPower(power);
        }
        prevA = a;

        // ---------------------------
        // INTAKE + WHEELS TOGGLE (gamepad2.x)
        // ---------------------------
        boolean x = gamepad2.x;
        if (x && !prevX) {
            intakeOn = !intakeOn;

            intake.setPower(intakeOn ? INTAKE_POWER : 0.0);

            if (intakeOn) {
                intakeWheelLeft.setPosition(INTAKE_WHEEL_LEFT_FORWARD);
                intakeWheelRight.setPosition(INTAKE_WHEEL_RIGHT_FORWARD);
            } else {
                intakeWheelLeft.setPosition(SERVO_STOP);
                intakeWheelRight.setPosition(SERVO_STOP);
            }
        }
        prevX = x;

        // ---------------------------
        // LIFT CONTROL (DPAD UP/DOWN)
        // ---------------------------
        boolean dUp = gamepad1.dpad_up;
        boolean dDown = gamepad1.dpad_down;

        if (dUp && !prevDpadUp) {
            liftRunning = !liftRunning;
            lift.setDirection(DcMotorSimple.Direction.FORWARD);
            lift.setPower(liftRunning ? LIFT_POWER : 0.0);
        }

        if (dDown && !prevDpadDown) {
            liftRunning = !liftRunning;
            lift.setDirection(DcMotorSimple.Direction.REVERSE);
            lift.setPower(liftRunning ? LIFT_POWER : 0.0);
        }

        prevDpadUp = dUp;
        prevDpadDown = dDown;

        // ---------------------------
        // DRIVE TRAIN (TANK DRIVE)
        // ---------------------------
        double leftPower = -gamepad1.left_stick_y;
        double rightPower = -gamepad1.right_stick_y;

        driveLeft.setPower(leftPower);
        driveRight.setPower(rightPower);

        // ---------------------------
        // TELEMETRY
        // ---------------------------
        telemetry.addData("Feeder ON", feederOn);
        telemetry.addData("Shooters ON", shootersOn);
        telemetry.addData("Intake ON", intakeOn);
        telemetry.addData("Lift Running", liftRunning);
        telemetry.update();
    }
}
