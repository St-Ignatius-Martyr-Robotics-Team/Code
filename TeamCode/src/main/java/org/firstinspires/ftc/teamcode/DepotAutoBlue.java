package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "DepotAutoBlue")
public class DepotAutoBlue extends LinearOpMode {

    // ===========================
    // HARDWARE
    // ===========================
    private DcMotor driveLeft;
    private DcMotor driveRight;
    private DcMotor shooterLeft;
    private DcMotor shooterRight;
    private DcMotor intake;
    private Servo intakeWheelLeft;
    private Servo intakeWheelRight;
    private Servo feeder;

    // ===========================
    // POWER CONSTANTS
    // ===========================
    private final double DRIVE_POWER = 0.5;
    private final double TURN_POWER = 1.0;
    private final double SHOOTER_POWER = 1.0;
    private final double INTAKE_POWER = -1.0;
    private final double INTAKE_WHEEL_LEFT_FORWARD = 1.0;
    private final double INTAKE_WHEEL_RIGHT_FORWARD = 0.0;

    private final double FEEDER_STOP = 0.5;
    private final double FEEDER_FORWARD = 1.0;

    // ===========================
    // TIMING CONSTANTS
    // ===========================
    private final long DRIVE_FORWARD_TIME_1 = 1700;
    private final long DRIVE_FORWARD_TIME_2 = 400;
    private final long TURN_180_TIME = 1600;
    private final long TURN_45_TIME = 600;
    private final long SHOOTER_SPINUP_TIME = 1400;
    private final long FEEDER_PUSH_TIME = 300;
    private final long FEEDER_RESET_TIME = 600;

    @Override
    public void runOpMode() {

        // ===========================
        // Map hardware
        // ===========================
        driveLeft = hardwareMap.get(DcMotor.class, "driveLeft");
        driveRight = hardwareMap.get(DcMotor.class, "driveRight");

        shooterLeft = hardwareMap.get(DcMotor.class, "shooterLeft");
        shooterRight = hardwareMap.get(DcMotor.class, "shooterRight");

        intake = hardwareMap.get(DcMotor.class, "intake");
        intakeWheelLeft = hardwareMap.get(Servo.class, "intakeWheelLeft");
        intakeWheelRight = hardwareMap.get(Servo.class, "intakeWheelRight");

        feeder = hardwareMap.get(Servo.class, "feeder");

        // ===========================
        // Directions
        // ===========================
        driveLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveRight.setDirection(DcMotorSimple.Direction.FORWARD);

        shooterLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterRight.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        // Stop servos/motors
        intake.setPower(0);
        intakeWheelLeft.setPosition(0.5);
        intakeWheelRight.setPosition(0.5);
        feeder.setPosition(FEEDER_STOP);

        telemetry.addLine("Autonomous Ready (Blue)");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        // ---------------------------
        // 1. DRIVE FORWARD
        // ---------------------------
        driveForward(DRIVE_FORWARD_TIME_1);

        // ---------------------------
        // 2. TURN 180 DEGREES (mirror: turn RIGHT)
        // ---------------------------
        turnRight(TURN_180_TIME);

        // ---------------------------
        // 3. SPIN UP SHOOTERS + INTAKE
        // ---------------------------
        shooterLeft.setPower(SHOOTER_POWER);
        shooterRight.setPower(SHOOTER_POWER);

        intake.setPower(INTAKE_POWER);
        intakeWheelLeft.setPosition(INTAKE_WHEEL_LEFT_FORWARD);
        intakeWheelRight.setPosition(INTAKE_WHEEL_RIGHT_FORWARD);

        sleep(SHOOTER_SPINUP_TIME);

        // ---------------------------
        // 4. SHOOT 3 TIMES WHILE INTAKE IS RUNNING
        // ---------------------------
        shootRing();
        shootRing();
        shootRing();

        // Stop shooter and intake after shooting
        shooterLeft.setPower(0);
        shooterRight.setPower(0);
        intake.setPower(0);
        intakeWheelLeft.setPosition(0.5);
        intakeWheelRight.setPosition(0.5);

        // ---------------------------
        // 5. TURN 45 DEGREES RIGHT (mirror)
        // ---------------------------
        turnRight(TURN_45_TIME);

        // ---------------------------
        // 6. DRIVE FORWARD
        // ---------------------------
        driveForward(DRIVE_FORWARD_TIME_2);

        // ---------------------------
        // 7. STOP
        // ---------------------------
        stopDrive();
    }

    // ===========================
    // HELPER METHODS
    // ===========================
    private void driveForward(long timeMs) {
        driveLeft.setPower(DRIVE_POWER);
        driveRight.setPower(DRIVE_POWER);
        sleep(timeMs);
        stopDrive();
    }

    private void turnLeft(long timeMs) {
        driveLeft.setPower(-TURN_POWER);
        driveRight.setPower(TURN_POWER);
        sleep(timeMs);
        stopDrive();
    }

    private void turnRight(long timeMs) {
        driveLeft.setPower(TURN_POWER);
        driveRight.setPower(-TURN_POWER);
        sleep(timeMs);
        stopDrive();
    }

    private void stopDrive() {
        driveLeft.setPower(0);
        driveRight.setPower(0);
    }

    private void shootRing() {
        feeder.setPosition(FEEDER_FORWARD);
        sleep(FEEDER_PUSH_TIME);
        feeder.setPosition(FEEDER_STOP);
        sleep(FEEDER_RESET_TIME);
    }
}
